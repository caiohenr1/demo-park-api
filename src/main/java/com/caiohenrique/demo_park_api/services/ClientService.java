package com.caiohenrique.demo_park_api.services;

import com.caiohenrique.demo_park_api.entities.Client;
import com.caiohenrique.demo_park_api.repositories.ClientRepository;
import com.caiohenrique.demo_park_api.exception.CpfUniqueViolationException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ClientService {
    public final ClientRepository clientRepository;

    @Transactional
    public Client create(Client client) {
        try {
            return clientRepository.save(client);
        } catch (DataIntegrityViolationException exception) {
            throw new CpfUniqueViolationException(
                    String.format("CPF %s não pode ser cadastrado, já existe no sistema", client.getCpf()));
        }
    }

}
