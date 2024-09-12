package com.caiohenrique.demo_park_pai.services;

import com.caiohenrique.demo_park_pai.entities.User;
import com.caiohenrique.demo_park_pai.repositories.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {
    public final UserRepository userRepository;

    @Transactional
    public User create(User user) {
        return userRepository.save(user);
    }

}
