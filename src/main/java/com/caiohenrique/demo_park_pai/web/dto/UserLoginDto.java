package com.caiohenrique.demo_park_pai.web.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class UserLoginDto {
    
    @Email(message = "FORMATO DE EMAIL INVÁLIDO", regexp = "^[a-z0-9.+-]+@[a-z0-9.-]+\\.[a-z]{2,}$")
    @NotBlank
    private String username;

    @NotBlank
    @Size(min = 6, max = 6)
    private String password;
}
