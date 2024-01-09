package com.lullaby.cardstudy.appliation.cardset.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.lullaby.cardstudy.domain.cardset.CardSet;
import com.lullaby.cardstudy.domain.cardset.CardSetType;

import java.time.LocalDateTime;

public record CardSetResponse(
        Long id,
        String name,
        String description,
        Integer totalCardCount,
        Integer toStudyCardCount,
        CardSetType type,
        @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
        LocalDateTime createdAt,
        @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
        LocalDateTime updatedAt
) {
    public CardSetResponse(CardSet cardSet) {
        this(
                cardSet.getId(),
                cardSet.getName(),
                cardSet.getDescription(),
                cardSet.getTotalCardCount(),
                cardSet.getToStudyCardCount(),
                cardSet.getType(),
                cardSet.getCreatedAt(),
                cardSet.getUpdatedAt()
        );
    }
}
