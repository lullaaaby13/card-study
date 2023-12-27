package com.lullaby.cardstudy.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.lullaby.cardstudy.domain.Card;
import com.lullaby.cardstudy.domain.MemorizationLevel;

import java.time.LocalDateTime;

public record CardResponse(
        Long id,
        String front,
        String back,
        MemorizationLevel memorizationLevel,
        @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
        LocalDateTime nextReviewDate,
        // CardSetResponse cardSet,
        @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
        LocalDateTime createdAt,
        @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
        LocalDateTime updatedAt
) {

    public CardResponse(Card card) {
        this(
                card.getId(),
                card.getFront(),
                card.getBack(),
                card.getMemorizationLevel(),
                card.getNextReviewAt(),
                // new CardSetResponse(card.getCardSet()),
                card.getCreatedAt(),
                card.getUpdatedAt()
        );
    }
}
