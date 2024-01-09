package com.lullaby.cardstudy.appliation.card.word.dto;

public record UpdateWordCardCommand(
        String question,
        String answer
) {
}
