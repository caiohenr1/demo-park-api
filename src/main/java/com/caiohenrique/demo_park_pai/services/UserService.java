package com.caiohenrique.demo_park_pai.services;

import com.caiohenrique.demo_park_pai.entities.User;
import com.caiohenrique.demo_park_pai.repositories.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {
    public final UserRepository userRepository;

    @Transactional
    public User create(User user) {
        return userRepository.save(user);
    }

    public User findById(Long id) {
        return userRepository
                .findById(id)
                .orElseThrow(() -> new RuntimeException("USUÁRIO NÃO ENCONTRADO"));
    }

    public List<User> findAll() {
        return userRepository.findAll();
    }

    @Transactional
    public User updatePassword(Long id, String currentPassword, String newPassword, String confirmPassword) {
        if (!newPassword.equals(confirmPassword)) {
            throw new RuntimeException("SENHA E CONFIRMAÇÃO DE SENHA NÃO CONFEREM");
        }

        User user = findById(id);
        if (!currentPassword.equals(user.getPassword())) {
            throw new RuntimeException("SENHA ATUAL NÃO CONFERE");
        }
        user.setPassword(newPassword);
        userRepository.save(user);
        return user;
    }


    public void delete(Long id) {
        findById(id);
        try {
            userRepository.deleteById(id);
        }catch (Exception exception) {
            throw new RuntimeException("USUÁRIO NÃO PODE SER EXCLUÍDO, HÁ RELAÇÃO COM OUTRA ENTIDADE");
        }
    }

}
