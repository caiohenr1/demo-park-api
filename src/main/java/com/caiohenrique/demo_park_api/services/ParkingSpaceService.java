package com.caiohenrique.demo_park_api.services;

import com.caiohenrique.demo_park_api.entities.ParkingSpace;
import com.caiohenrique.demo_park_api.exception.EntityNotFoundException;
import com.caiohenrique.demo_park_api.repositories.ParkingSpaceRepository;

import com.caiohenrique.demo_park_pai.exception.CodeUniqueViolationException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ParkingSpaceService {
    private final ParkingSpaceRepository parkingSpaceRepository;

    @Transactional
    public ParkingSpace create (ParkingSpace parkingSpace) {
        try {
            return parkingSpaceRepository.save(parkingSpace);
        } catch (DataIntegrityViolationException exception) {
                throw new CodeUniqueViolationException(
                        String.format("Vaga com código %s já cadastrada", parkingSpace.getCode()));
        }
    }


    public ParkingSpace findByCode (String code) {
        return parkingSpaceRepository.findByCode(code)
                .orElseThrow(() -> new EntityNotFoundException(
                        String.format("Vaga com código %s não encontrada", code)));
    }
}
