package com.blabbo.app.blabbo.config;

import com.blabbo.app.blabbo.model.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Collections;

public class CustomUserDetails implements UserDetails {

    private final User user;


    public CustomUserDetails(User user) {
        this.user = user;
    }


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.emptyList();
    }


    @Override
    public String getPassword() {
        return user.getPassword();
    }


    @Override
    public String getUsername() {
        return user.getEmail();
    }

}
