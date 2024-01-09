package com.lullaby.cardstudy.appliation.card.choice.dto;

public record UpdateChoiceCommand(
        Integer sequence,
        String content,
        Boolean isAnswer
) {
}
