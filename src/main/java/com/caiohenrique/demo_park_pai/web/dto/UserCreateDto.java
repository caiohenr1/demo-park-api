package com.caiohenrique.demo_park_pai.web.dto;

import lombok.*;
import org.hibernate.internal.util.StringHelper;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class UserCreateDto {

    private String username;
    private String password;
}
