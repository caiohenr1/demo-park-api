package com.caiohenrique.demo_park_pai.services;

import com.caiohenrique.demo_park_pai.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {
    public final UserRepository userRepository;


}
