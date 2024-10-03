package com.caiohenrique.demo_park_api.repositories;

import com.caiohenrique.demo_park_api.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUsername(String username);

    @Query("select u.role from User u where u.username like :username")
    User.Role findRoleByUsername(String username);

}
