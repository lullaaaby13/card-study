package com.lullaby.cardstudy.dto;

import com.lullaby.cardstudy.domain.Category;

public record CategoryResponse(
        Long id,
        String name,
        String description,
        String createdAt,
        String updatedAt
) {
    public CategoryResponse(Category category) {
        this(
                category.getId(),
                category.getName(),
                category.getDescription(),
                category.getCreatedAt().toString(),
                category.getUpdatedAt().toString()
        );
    }
}
