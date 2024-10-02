package com.caiohenrique.demo_park_pai.web.controllers;

import com.caiohenrique.demo_park_pai.entities.User;
import com.caiohenrique.demo_park_pai.services.UserService;
import com.caiohenrique.demo_park_pai.web.dto.UserCreateDto;
import com.caiohenrique.demo_park_pai.web.dto.UserResponseDto;
import com.caiohenrique.demo_park_pai.web.dto.UserUpdatePasswordDto;
import com.caiohenrique.demo_park_pai.web.dto.mapper.UserMapper;
import com.caiohenrique.demo_park_pai.web.exeption.ErrorMessage;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Users", description =
        "Contém todas as operações relativas aos recursos para cadastro, edição, remoção e leitura de um usuário")
@RestController
@RequestMapping("api/v1/users")
@RequiredArgsConstructor
public class UserController {

    public final UserService userService;

    @Operation(
            summary = "Criar um novo usuário", description = "Recurso para criar um novo usuário",
            responses = {
                    @ApiResponse(responseCode = "201", description = "Recurso criado com sucesso",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = UserResponseDto.class))),
                    @ApiResponse(responseCode = "409", description = "Usuário email já cadastrado no sistema",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = ErrorMessage.class))),
                    @ApiResponse(responseCode = "422", description = "Recurso não processado por dados de entrada inválidos",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = ErrorMessage.class)))
            }
    )
    @PostMapping
    public ResponseEntity<UserResponseDto> create(@Valid @RequestBody UserCreateDto userCreateDto) {
        User newUser = userService.create(UserMapper.toUser(userCreateDto));
        return ResponseEntity.status(HttpStatus.CREATED).body(UserMapper.toResponseDto(newUser));
    }

    @Operation(
            summary = "Buscar um usuário pelo id", description = "Requisição exige um Bearer Token. Acesso restrito a ADMIN | CLIENTE",
            security = @SecurityRequirement(name = "security"),
            responses = {
                    @ApiResponse(responseCode = "200", description = "Usuário localizado com sucesso",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = UserResponseDto.class))),
                    @ApiResponse(responseCode = "403", description = "Usuário sem permissão para acessar esse recurso",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = ErrorMessage.class))),
                    @ApiResponse(responseCode = "404", description = "Recurso não encontrado",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = ErrorMessage.class)))
            }
    )

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN') OR (hasRole('CLIENT') AND #id == authentication.principal.id)")
    public ResponseEntity<UserResponseDto> findById(@PathVariable Long id) {
        User user = userService.findById(id);
        return ResponseEntity.ok().body(UserMapper.toResponseDto(user));
    }

    @Operation(
            summary = "Recuperar todos os usuários cadastrados", description = "Requisição exige um Bearer Token. Acesso restrito a ADMIN",
            security = @SecurityRequirement(name = "security"),
            responses = {
                    @ApiResponse(responseCode = "200", description = "Recurso recuperado com sucesso",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = UserResponseDto.class))),
                    @ApiResponse(responseCode = "403", description = "Usuário sem permissão para acessar esse recurso",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = ErrorMessage.class)))
            }
    )
    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<UserResponseDto>> findAll() {
        List<User> userList = userService.findAll();
        return ResponseEntity.ok().body(UserMapper.toListResponseDto(userList));
    }

    @Operation(
            summary = "Deletar um usuário pelo id", description = "Requisição exige um Bearer Token. Acesso restrito a ADMIN",
            security = @SecurityRequirement(name = "security"),
            responses = {
                    @ApiResponse(responseCode = "200", description = "Recurso deletado com sucesso",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = ErrorMessage.class))),
                    @ApiResponse(responseCode = "403", description = "Usuário sem permissão para acessar esse recurso",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = ErrorMessage.class))),
                    @ApiResponse(responseCode = "404", description = "Recurso não encontrado",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = ErrorMessage.class)))
            }
    )
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        userService.delete(id);
        return ResponseEntity.ok().build();
    }

    @Operation(
            summary = "Alterar a senha de um usuário", description = "Requisição exige um Bearer Token. Acesso restrito a ADMIN | CLIENTE",
            security = @SecurityRequirement(name = "security"),
            responses = {
                    @ApiResponse(responseCode = "422", description = "Senha deve ser de apenas 6 caracteres",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = ErrorMessage.class))),
                    @ApiResponse(responseCode = "400", description = "Senha atual não confere",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = ErrorMessage.class))),
                    @ApiResponse(responseCode = "400", description = "Nova senha e confirmação de senha não conferem",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = ErrorMessage.class))),
                    @ApiResponse(responseCode = "403", description = "Usuário sem permissão para acessar esse recurso",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = ErrorMessage.class))),
                    @ApiResponse(responseCode = "200", description = "Recurso alterado com sucesso",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = Void.class))),
            }
    )
    @PatchMapping("/{id}/change-password")
    @PreAuthorize("hasAnyRole('ADMIN', 'CLIENT') AND (#id == authentication.principal.id)")
    public ResponseEntity<UserResponseDto> updatePassword(@PathVariable Long id, @Valid @RequestBody UserUpdatePasswordDto updatePasswordDto) {
        User user1 = userService
                .updatePassword(id,
                        updatePasswordDto.getCurrentPassword()
                        , updatePasswordDto.getNewPassword()
                        , updatePasswordDto.getConfirmPassword());
        return ResponseEntity.ok().body(UserMapper.toResponseDto(user1));
    }
}
