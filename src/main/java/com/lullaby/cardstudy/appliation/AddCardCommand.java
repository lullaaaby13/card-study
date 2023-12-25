package com.lullaby.cardstudy.appliation;

public record AddCardCommand(
        Long cardSetId,
        String front,
        String back
) {
}
