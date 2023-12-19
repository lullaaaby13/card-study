package com.lullaby.cardstudy.appliation;

public record AddCardCommand(
        Long categoryId,
        String front,
        String back
) {
}
