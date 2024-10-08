package com.caiohenrique.demo_park_api.web.controllers;

import com.caiohenrique.demo_park_api.entities.Client;
import com.caiohenrique.demo_park_api.jwt.JwtUserDetails;
import com.caiohenrique.demo_park_api.repositories.ClientRepository;
import com.caiohenrique.demo_park_api.services.ClientService;
import com.caiohenrique.demo_park_api.services.UserService;
import com.caiohenrique.demo_park_api.web.dto.ClientCreateDto;
import com.caiohenrique.demo_park_api.web.dto.ClientResponseDto;
import com.caiohenrique.demo_park_api.web.dto.mapper.ClientMapper;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1/clients")
@RequiredArgsConstructor
public class ClientController {
    private final ClientService clientService;
    private final UserService userService;

    @PostMapping
    @PreAuthorize("hasRole('CLIENT')")
    public ResponseEntity<ClientResponseDto> create (
            @RequestBody @Valid ClientCreateDto clientCreateDto,
            @AuthenticationPrincipal JwtUserDetails userDetails) {
        Client client = ClientMapper.toClient(clientCreateDto);
        client.setUser(userService.findById(userDetails.getId()));
        clientService.create(client);
        return ResponseEntity.status(201).body(ClientMapper.toClientResponseDto(client));
    }



}
