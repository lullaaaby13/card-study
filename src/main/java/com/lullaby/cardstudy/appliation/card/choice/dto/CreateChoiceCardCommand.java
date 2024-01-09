package com.lullaby.cardstudy.appliation.card.choice.dto;

import java.util.List;

public record CreateChoiceCardCommand(
        Long cardSetId,
        String question,
        String answer,
        List<Choice> choices

    ) {
    public record Choice(
            Integer sequence,
            String content,
            Boolean isAnswer
    ) {
    }
}
