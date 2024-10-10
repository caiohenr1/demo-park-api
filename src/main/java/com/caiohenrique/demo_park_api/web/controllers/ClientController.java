package com.caiohenrique.demo_park_api.web.controllers;

import com.caiohenrique.demo_park_api.entities.Client;
import com.caiohenrique.demo_park_api.jwt.JwtUserDetails;
import com.caiohenrique.demo_park_api.services.ClientService;
import com.caiohenrique.demo_park_api.services.UserService;
import com.caiohenrique.demo_park_api.web.dto.ClientCreateDto;
import com.caiohenrique.demo_park_api.web.dto.ClientResponseDto;
import com.caiohenrique.demo_park_api.web.dto.UserResponseDto;
import com.caiohenrique.demo_park_api.web.dto.mapper.ClientMapper;
import com.caiohenrique.demo_park_api.web.exeption.ErrorMessage;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Clientes", description = "Contém todas as operações relativas ao recurso de um cliente")
@RestController
@RequestMapping("api/v1/clients")
@RequiredArgsConstructor
public class ClientController {
    private final ClientService clientService;
    private final UserService userService;

    @Operation(
            summary = "Criar um novo cliente", description = "Recurso para criar um novo cliente vinculado a um usuário cadastrado. " +
            "Requisição exige uso de um bearer token. Acesso restrito a Role='CLIENTE'",
            responses = {
                    @ApiResponse(responseCode = "201", description = "Recurso criado com sucesso",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = UserResponseDto.class))),
                    @ApiResponse(responseCode = "409", description = "Cliente CPF já possuí cadastro no sistema",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = ErrorMessage.class))),
                    @ApiResponse(responseCode = "422", description = "Recurso não processado por  falta de dados ou dados inválidos",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = ErrorMessage.class))),
                    @ApiResponse(responseCode = "403", description = "Recurso não permitido ao perfil de ADMIN",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = ErrorMessage.class)))
            }
    )

    @PostMapping
    @PreAuthorize("hasRole('CLIENT')")
    public ResponseEntity<ClientResponseDto> create(
            @RequestBody @Valid ClientCreateDto clientCreateDto,
            @AuthenticationPrincipal JwtUserDetails userDetails) {
        Client client = ClientMapper.toClient(clientCreateDto);
        client.setUser(userService.findById(userDetails.getId()));
        clientService.create(client);
        return ResponseEntity.status(201).body(ClientMapper.toClientResponseDto(client));
    }

    @Operation(
            summary = "Localizar um cliente", description = "Recurso para localizar um cliente pelo ID. " +
            "Requisição exige uso de um bearer token. Acesso restrito a Role='ADMIN'",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Recurso localizado com sucesso",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = UserResponseDto.class))),
                    @ApiResponse(responseCode = "404", description = "Cliente não encontrado",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = ErrorMessage.class))),
                    @ApiResponse(responseCode = "403", description = "Recurso não permitido ao perfil de CLIENTE",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = ErrorMessage.class)))
            }
    )

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ClientResponseDto> findById(@PathVariable Long id) {
        Client client = clientService.findById(id);
        return ResponseEntity.ok(ClientMapper.toClientResponseDto(client));
    }

    @GetMapping
    @PreAuthorize(("hasRole('ADMIN')"))
    public ResponseEntity<List<ClientResponseDto>> findAll() {
        List<Client> clients = clientService.findAll();
        return ResponseEntity.ok().body(ClientMapper.toListClientResponseDto(clients));
    }
}
