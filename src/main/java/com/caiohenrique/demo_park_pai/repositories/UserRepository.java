package com.caiohenrique.demo_park_pai.repositories;

import com.caiohenrique.demo_park_pai.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
}
