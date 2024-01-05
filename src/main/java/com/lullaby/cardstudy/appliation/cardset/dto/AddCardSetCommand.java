package com.lullaby.cardstudy.appliation.cardset.dto;

import com.lullaby.cardstudy.domain.cardset.CardSetType;

public record AddCardSetCommand(
        CardSetType type,
        String name,
        String description
) {
}
