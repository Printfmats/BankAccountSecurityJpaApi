package com.example.demo.security;

import com.example.demo.entity.UserBankLogger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;

public class UserSecurity implements UserDetails {

    private final UserBankLogger userBankLogger;
    @Autowired
    public UserSecurity(UserBankLogger userBankLogger) {
        this.userBankLogger = userBankLogger;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
    }

    public String getAuthoritie() {
        return userBankLogger.getLogin();
    }

    @Override
    public String getPassword() {
        return userBankLogger.getPassword();
    }

    @Override
    public String getUsername() {
        return userBankLogger.getLogin();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
