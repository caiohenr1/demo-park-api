package com.caiohenrique.demo_park_api.web.dto.mapper;

import com.caiohenrique.demo_park_api.entities.Client;
import com.caiohenrique.demo_park_api.web.dto.ClientCreateDto;
import com.caiohenrique.demo_park_api.web.dto.ClientResponseDto;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.modelmapper.ModelMapper;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ClientMapper {

    public static Client toClient(ClientCreateDto clientCreateDto ){
        return new ModelMapper().map(clientCreateDto, Client.class);
    }

    public static ClientResponseDto toClientResponseDto (Client client) {
        return new ModelMapper().map(client, ClientResponseDto.class);
    }
}
