package com.caiohenrique.demo_park_api.repositories;

import com.caiohenrique.demo_park_api.entities.ParkingSpace;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ParkingSpaceRepository extends JpaRepository<ParkingSpace, Long> {
    Optional<ParkingSpace> findByCode(String code);
}
