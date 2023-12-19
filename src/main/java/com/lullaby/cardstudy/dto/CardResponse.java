package com.lullaby.cardstudy.dto;

import com.lullaby.cardstudy.domain.Card;
import com.lullaby.cardstudy.domain.MemorizationLevel;

import java.time.LocalDateTime;

public record CardResponse(
        Long id,
        String front,
        String back,

        MemorizationLevel memorizationLevel,
        LocalDateTime nextReviewDate,
        CategoryResponse category,
        String createdAt,
        String updatedAt
) {

    public CardResponse(Card card) {
        this(
                card.getId(),
                card.getFront(),
                card.getBack(),
                card.getMemorizationLevel(),
                card.getNextReviewAt(),
                new CategoryResponse(card.getCategory()),
                card.getCreatedAt().toString(),
                card.getUpdatedAt().toString()
        );
    }
}
