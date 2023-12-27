package com.lullaby.cardstudy.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.lullaby.cardstudy.domain.CardSet;

import java.time.LocalDateTime;

public record CardSetResponse(
        Long id,
        String name,
        String description,
        Integer totalCardCount,
        Integer toStudyCardCount,
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
                cardSet.getCreatedAt(),
                cardSet.getUpdatedAt()
        );
    }
}
