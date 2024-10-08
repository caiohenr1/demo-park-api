package com.caiohenrique.demo_park_api.services;

import com.caiohenrique.demo_park_api.entities.User;
import com.caiohenrique.demo_park_api.exception.EntityNotFoundException;
import com.caiohenrique.demo_park_api.exception.PasswordInvalidException;
import com.caiohenrique.demo_park_api.exception.UserNameUniqueViolationException;
import com.caiohenrique.demo_park_api.repositories.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public User create(User user) {
        try { //exceção p/ usuario que já existe ( username unique)
            user.setPassword(passwordEncoder.encode(user.getPassword()));
            return userRepository.save(user);
        } catch (DataIntegrityViolationException exception) {
            throw new UserNameUniqueViolationException(String.format("Username {%s} já cadastrado", user.getUsername()));
        }
    }

    public User findById(Long id) {
        return userRepository
                .findById(id)
                .orElseThrow(() -> new EntityNotFoundException(String.format("USUÁRIO ID: (%s), NÃO ENCONTRADO", id)));
    }

    public List<User> findAll() {
        return userRepository.findAll();
    }

    @Transactional
    public User updatePassword(Long id, String currentPassword, String newPassword, String confirmPassword) {
        if (!newPassword.equals(confirmPassword)) {
            throw new PasswordInvalidException("SENHA E CONFIRMAÇÃO DE SENHA NÃO CONFEREM");
        }

        User user = findById(id);
        if (!passwordEncoder.matches(currentPassword, user.getPassword())) {
            throw new PasswordInvalidException("SENHA ATUAL NÃO CONFERE");
        }
        user.setPassword(passwordEncoder.encode(newPassword));
//        userRepository.save(user);
        return user;
    }


    @Transactional
    public void delete(Long id) {
        findById(id);
        try {
            userRepository.deleteById(id);
        } catch (Exception exception) {
            throw new RuntimeException("USUÁRIO NÃO PODE SER EXCLUÍDO, HÁ RELAÇÃO COM OUTRA ENTIDADE");
        }
    }


    public User findByUsername(String username) {
        return userRepository
                .findByUsername(username)
                .orElseThrow(() -> new EntityNotFoundException(String.format("Usuário com %s não encontrado", username)));
    }


    public User.Role findRoleByUsername(String username) {
        return userRepository.findRoleByUsername(username);
    }
}
