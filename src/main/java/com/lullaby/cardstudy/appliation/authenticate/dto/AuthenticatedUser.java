package com.lullaby.cardstudy.appliation.authenticate.dto;

import java.security.Principal;

public class AuthenticatedUser implements Principal {

    private final Long userId;

    public AuthenticatedUser(Long userId) {
        this.userId = userId;
    }

    @Override
    public String getName() {
        return userId.toString();
    }

    public Long getUserId() {
        return userId;
    }
}
