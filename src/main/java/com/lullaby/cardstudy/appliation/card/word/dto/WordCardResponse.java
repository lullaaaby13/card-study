package com.lullaby.cardstudy.appliation.card.word.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.lullaby.cardstudy.domain.card.MemorizationLevel;
import com.lullaby.cardstudy.domain.card.word.WordCard;

import java.time.LocalDateTime;

public record WordCardResponse(
        Long id,
        String question,
        String answer,
        MemorizationLevel memorizationLevel,
        @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
        LocalDateTime nextReviewDate,
        // CardSetResponse cardSet,
        @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
        LocalDateTime createdAt,
        @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
        LocalDateTime updatedAt
) {

    public WordCardResponse(WordCard card) {
        this(
                card.getId(),
                card.getQuestion(),
                card.getAnswer(),
                card.getMemorizationLevel(),
                card.getNextReviewAt(),
                // new CardSetResponse(card.getCardSet()),
                card.getCreatedAt(),
                card.getUpdatedAt()
        );
    }
}
