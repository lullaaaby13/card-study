package com.lullaby.cardstudy.appliation.card.dto;

public record AddCardCommand(
        Long cardSetId,
        String front,
        String back
) {
}
