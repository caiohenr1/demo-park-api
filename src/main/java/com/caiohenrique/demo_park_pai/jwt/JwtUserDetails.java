package com.caiohenrique.demo_park_pai.jwt;

import com.caiohenrique.demo_park_pai.entities.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;

import java.util.Collection;

public class JwtUserDetails extends org.springframework.security.core.userdetails.User {
    private User user;

    public JwtUserDetails(User user) {
        super(
                user.getUsername(),
                user.getPassword(),
                AuthorityUtils.createAuthorityList(user.getRole().name())

        );
        this.user = user;
    }

    public Long getId() {
        return this.user.getId();
    }

    public String getRole() {
        return user.getRole().name();
    }
}
