package com.caiohenrique.demo_park_api.web.controllers;

import com.caiohenrique.demo_park_api.entities.ParkingSpace;
import com.caiohenrique.demo_park_api.services.ParkingSpaceService;
import com.caiohenrique.demo_park_api.web.dto.ParkingSpaceCreateDto;
import com.caiohenrique.demo_park_api.web.dto.ParkingSpaceResponseDto;
import com.caiohenrique.demo_park_api.web.dto.mapper.ParkingSpaceMapper;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping("api/v1/parkingSpaces")
@RequiredArgsConstructor
public class ParkingSpaceController {
    private final ParkingSpaceService parkingSpaceService;

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> create (@RequestBody @Valid  ParkingSpaceCreateDto parkingSpaceCreateDto) {
        ParkingSpace parkingSpace = ParkingSpaceMapper.toParkingSpace(parkingSpaceCreateDto);
        parkingSpaceService.create(parkingSpace);
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequestUri().path("/{code}")
                .buildAndExpand(parkingSpace.getCode())
                .toUri();
        return ResponseEntity.created(location).build();
    }

    @GetMapping("/{code}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ParkingSpaceResponseDto> findByCode (@PathVariable String code) {
      ParkingSpace parkingSpace = parkingSpaceService.findByCode(code);
        return ResponseEntity.ok(ParkingSpaceMapper.toParkingSpaceRespondeDto(parkingSpace));
    }
}
