package com.caiohenrique.demo_park_api.web.controllers;

import com.caiohenrique.demo_park_api.entities.ParkingSpace;
import com.caiohenrique.demo_park_api.services.ParkingSpaceService;
import com.caiohenrique.demo_park_api.web.dto.ParkingSpaceCreateDto;
import com.caiohenrique.demo_park_api.web.dto.ParkingSpaceResponseDto;
import com.caiohenrique.demo_park_api.web.dto.mapper.ParkingSpaceMapper;
import com.caiohenrique.demo_park_api.web.exeption.ErrorMessage;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.headers.Header;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@Tag(name = "Vagas", description = "Contém todas as operações relativas ao recurso de uma vaga")
@RestController
@RequestMapping("api/v1/parkingSpaces")
@RequiredArgsConstructor
public class ParkingSpaceController {
    private final ParkingSpaceService parkingSpaceService;


    @Operation(
            summary = "Criar uma nova vaga",
            description = "Recurso para criar uma nova vaga. " + "Requisição exige uso de um bearer token. Acesso restrito a Role='ADMIN'",
            security = @SecurityRequirement(name = "security"),
            responses = {
                    @ApiResponse(responseCode = "201", description = "Recurso criado com sucesso",
                            headers = @Header(name = HttpHeaders.LOCATION, description = "URL do recurso criado")),
                    @ApiResponse(responseCode = "409", description = "Vaga já cadastrada",
                            content = @Content(mediaType = "application/json;charset=UTF-8",
                                    schema = @Schema(implementation = ErrorMessage.class))),
                    @ApiResponse(responseCode = "422", description = "Recurso não processado por  falta de dados ou dados inválidos",
                            content = @Content(mediaType = "application/json;charset=UTF-8",
                                    schema = @Schema(implementation = ErrorMessage.class)))
            }
    )

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> create(@RequestBody @Valid ParkingSpaceCreateDto parkingSpaceCreateDto) {
        ParkingSpace parkingSpace = ParkingSpaceMapper.toParkingSpace(parkingSpaceCreateDto);
        parkingSpaceService.create(parkingSpace);
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequestUri().path("/{code}")
                .buildAndExpand(parkingSpace.getCode())
                .toUri();
        return ResponseEntity.created(location).build();
    }

    @Operation(
            summary = "Localizar uma vaga",
            description = "Recurso para localizar uma vaga pelo seu código. " +
                    "Requisição exige uso de um bearer token. Acesso restrito a Role='ADMIN'",
            security = @SecurityRequirement(name = "security"),
            responses = {
                    @ApiResponse(responseCode = "200", description = "Recurso localizado com sucesso",
                            content = @Content(mediaType = "application/json;charset=UTF-8",
                                    schema = @Schema(implementation = ParkingSpaceResponseDto.class))),
                    @ApiResponse(responseCode = "404", description = "Vaga não localizada",
                            content = @Content(mediaType = "application/json;charset=UTF-8",
                                    schema = @Schema(implementation = ErrorMessage.class)))
            }
    )
    @GetMapping("/{code}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ParkingSpaceResponseDto> findByCode(@PathVariable String code) {
        ParkingSpace parkingSpace = parkingSpaceService.findByCode(code);
        return ResponseEntity.ok(ParkingSpaceMapper.toParkingSpaceRespondeDto(parkingSpace));
    }
}
