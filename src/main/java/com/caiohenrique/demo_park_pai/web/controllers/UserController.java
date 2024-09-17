package com.caiohenrique.demo_park_pai.web.controllers;

import com.caiohenrique.demo_park_pai.entities.User;
import com.caiohenrique.demo_park_pai.services.UserService;
import com.caiohenrique.demo_park_pai.web.dto.UserCreateDto;
import com.caiohenrique.demo_park_pai.web.dto.UserResponseDto;
import com.caiohenrique.demo_park_pai.web.dto.UserUpdatePasswordDto;
import com.caiohenrique.demo_park_pai.web.dto.mapper.UserMapper;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Users", description = "Contém todas as operações relativas aos recursos para cadastro, edição, remoção e leitura de um usuário")
@RestController
@RequestMapping("api/v1/users")
@RequiredArgsConstructor
public class UserController {

    public final UserService userService;

    @Operation(
            summary = "Criar um novo usuário", description = "Recurso para criar um novo usuário",
            responses = {
                    @ApiResponse(responseCode = "201", description = "Recurso criado com sucesso",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = UserResponseDto.class)))
            }
    )
    @PostMapping
    public ResponseEntity<UserResponseDto> create(@Valid @RequestBody UserCreateDto userCreateDto) {
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
    public ResponseEntity<UserResponseDto> updatePassword(@PathVariable Long id, @Valid @RequestBody UserUpdatePasswordDto updatePasswordDto) {
        User user1 = userService
                .updatePassword(id,
                        updatePasswordDto.getCurrentPassword()
                        , updatePasswordDto.getNewPassword()
                        , updatePasswordDto.getConfirmPassword());
        return ResponseEntity.ok().body(UserMapper.toResponseDto(user1));
    }


}
