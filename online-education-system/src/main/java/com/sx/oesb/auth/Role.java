package com.sx.oesb.auth;

import org.springframework.security.core.GrantedAuthority;

public enum Role implements GrantedAuthority {
    UNCERTIFIED, FROZEN, USER, ADMIN;

    @Override
    public String getAuthority() {
        return name();
    }
}
