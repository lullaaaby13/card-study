package com.lullaby.cardstudy.dto;

import com.lullaby.cardstudy.domain.CardSet;

public record CardSetResponse(
        Long id,
        String name,
        String description,
        String createdAt,
        String updatedAt
) {
    public CardSetResponse(CardSet cardSet) {
        this(
                cardSet.getId(),
                cardSet.getName(),
                cardSet.getDescription(),
                cardSet.getCreatedAt().toString(),
                cardSet.getUpdatedAt().toString()
        );
    }
}
