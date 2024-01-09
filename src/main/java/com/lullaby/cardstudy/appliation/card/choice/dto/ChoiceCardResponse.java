package com.lullaby.cardstudy.appliation.card.choice.dto;

import com.lullaby.cardstudy.domain.card.choice.Choice;
import com.lullaby.cardstudy.domain.card.choice.ChoiceCard;

import java.util.List;

public record ChoiceCardResponse(
        Long id,
        String question,
        String answer,
        List<ChoiceResponse> choices

) {

    public ChoiceCardResponse(ChoiceCard card) {
        this(
                card.getId(),
                card.getQuestion(),
                card.getAnswer(),
                card.getChoices().stream().map(ChoiceResponse::new).toList()
        );
    }
    record ChoiceResponse(
            Integer order,
            String content,
            Boolean isAnswer
    ) {
        public ChoiceResponse(Choice choice) {
            this(
                    choice.getSequence(),
                    choice.getContent(),
                    choice.getIsAnswer()
            );
        }
    }
}
