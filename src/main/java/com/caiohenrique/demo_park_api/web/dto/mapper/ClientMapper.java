package com.caiohenrique.demo_park_api.web.dto.mapper;

import com.caiohenrique.demo_park_api.entities.Client;
import com.caiohenrique.demo_park_api.entities.User;
import com.caiohenrique.demo_park_api.web.dto.ClientCreateDto;
import com.caiohenrique.demo_park_api.web.dto.ClientResponseDto;
import com.caiohenrique.demo_park_api.web.dto.UserResponseDto;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.modelmapper.ModelMapper;

import java.util.List;
import java.util.stream.Collectors;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ClientMapper {

    public static Client toClient(ClientCreateDto clientCreateDto ){
        return new ModelMapper().map(clientCreateDto, Client.class);
    }

    public static ClientResponseDto toClientResponseDto (Client client) {
        return new ModelMapper().map(client, ClientResponseDto.class);
    }

    public static List<ClientResponseDto> toListClientResponseDto (List<Client> clients) {
        return clients.stream().map(user -> toClientResponseDto(user)).collect(Collectors.toList());
    }
}
