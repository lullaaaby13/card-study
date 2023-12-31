package com.lullaby.cardstudy.appliation.authenticate.dto;

public record SignInCommand(
        String account,
        String password) {
}
