package com.caiohenrique.demo_park_api.web.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class UserResponseDto {
    private Long id;
    private String username;
    private String role;
}