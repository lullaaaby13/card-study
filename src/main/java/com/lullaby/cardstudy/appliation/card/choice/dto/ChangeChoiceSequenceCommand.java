package com.lullaby.cardstudy.appliation.card.choice.dto;

public record ChangeChoiceSequenceCommand(
        Integer sequence,
        Integer newSequence) {
}
