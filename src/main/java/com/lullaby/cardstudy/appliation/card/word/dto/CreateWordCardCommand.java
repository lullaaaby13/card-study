package com.lullaby.cardstudy.appliation.card.word.dto;

public record CreateWordCardCommand(
        Long cardSetId,
        String question,
        String answer
) {
}
