package com.caiohenrique.demo_park_api.web.controllers;

import com.caiohenrique.demo_park_api.jwt.JwtToken;
import com.caiohenrique.demo_park_api.jwt.JwtUserDetailsService;
import com.caiohenrique.demo_park_api.web.dto.UserLoginDto;
import com.caiohenrique.demo_park_api.web.dto.UserResponseDto;
import com.caiohenrique.demo_park_api.web.exeption.ErrorMessage;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "Autenticação", description = "Recurso para relizar a autenticação na API")
@Slf4j
@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor

public class AuthenticationController {
    private final JwtUserDetailsService jwtUserDetailsService;
    private final AuthenticationManager authenticationManager;

    @Operation(
            summary = "Autenticar na API", description = "Recurso de autenticação na API",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Autenticação realizada com sucesso e retorno de um Bearer token",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = UserResponseDto.class))),
                    @ApiResponse(responseCode = "400", description = "Credenciais inválidas",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = ErrorMessage.class))),
                    @ApiResponse(responseCode = "422", description = "Campos inválidos",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = ErrorMessage.class)))
            }
    )

    @PostMapping("auth")
    public ResponseEntity<?> authenticate(@RequestBody @Valid UserLoginDto userLoginDto, HttpServletRequest request) {
        log.info("Processo de autenticação pelo login {}", userLoginDto.getUsername());
        try {
            UsernamePasswordAuthenticationToken authenticationToken =
                    new UsernamePasswordAuthenticationToken(userLoginDto.getUsername(), userLoginDto.getPassword());
            authenticationManager.authenticate(authenticationToken);
            JwtToken token = jwtUserDetailsService.getTokenAuthenticated(userLoginDto.getUsername());
            return ResponseEntity.ok(token);
        } catch (AuthenticationException exception) {
            log.warn("Bad Credentials from username {}", userLoginDto.getUsername());
        }
        return ResponseEntity
                .badRequest()
                .body(new ErrorMessage(request, HttpStatus.BAD_REQUEST, "Credenciais inválidas"));
    }
}
