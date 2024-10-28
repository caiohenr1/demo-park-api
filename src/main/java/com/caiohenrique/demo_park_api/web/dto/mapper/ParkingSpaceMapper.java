package com.caiohenrique.demo_park_api.web.dto.mapper;

import com.caiohenrique.demo_park_api.entities.ParkingSpace;
import com.caiohenrique.demo_park_api.web.dto.ParkingSpaceCreateDto;
import com.caiohenrique.demo_park_api.web.dto.ParkingSpaceResponseDto;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.modelmapper.ModelMapper;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ParkingSpaceMapper {
    public static ParkingSpace toParkingSpace(ParkingSpaceCreateDto parkingSpaceCreateDto) {
        return new ModelMapper().map(parkingSpaceCreateDto, ParkingSpace.class);
    }

    public static ParkingSpaceResponseDto toParkingSpaceRespondeDto(ParkingSpace parkingSpace) {
        return new ModelMapper().map(parkingSpace, ParkingSpaceResponseDto.class);
    }
}
