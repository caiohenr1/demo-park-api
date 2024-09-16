package com.caiohenrique.demo_park_pai.web.controllers;

import com.caiohenrique.demo_park_pai.entities.User;
import com.caiohenrique.demo_park_pai.services.UserService;
import com.caiohenrique.demo_park_pai.web.dto.UserCreateDto;
import com.caiohenrique.demo_park_pai.web.dto.UserResponseDto;
import com.caiohenrique.demo_park_pai.web.dto.UserUpdatePasswordDto;
import com.caiohenrique.demo_park_pai.web.dto.mapper.UserMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/users")
@RequiredArgsConstructor
public class UserController {

    public final UserService userService;

    @PostMapping
    public ResponseEntity<UserResponseDto> create(@RequestBody UserCreateDto userCreateDto) {
        User newUser = userService.create(UserMapper.toUser(userCreateDto));
        return ResponseEntity.status(HttpStatus.CREATED).body(UserMapper.toResponseDto(newUser));
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserResponseDto> findById(@PathVariable Long id) {
        User user = userService.findById(id);
        return ResponseEntity.ok().body(UserMapper.toResponseDto(user));
    }

    @GetMapping
    public ResponseEntity<List<UserResponseDto>> findAll() {
        List<User> userList = userService.findAll();
        return ResponseEntity.ok().body(UserMapper.toListResponseDto(userList));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        userService.delete(id);
        return ResponseEntity.ok().build();
    }

    @PatchMapping("/{id}")
    public ResponseEntity<UserResponseDto> updatePassword(@PathVariable Long id, @RequestBody UserUpdatePasswordDto updatePasswordDto) {
        User user1 = userService
                .updatePassword(id,
                        updatePasswordDto.getCurrentPassword()
                        , updatePasswordDto.getNewPassword()
                        , updatePasswordDto.getConfirmPassword());
        return ResponseEntity.ok().body(UserMapper.toResponseDto(user1));
    }


}
