package com.caiohenrique.demo_park_pai.web.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserUpdatePasswordDto {
    private String currentPassword;
    private String newPassword;
    private String confirmPassword;
}
