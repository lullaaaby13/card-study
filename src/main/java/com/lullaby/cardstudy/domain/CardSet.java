package com.lullaby.cardstudy.domain;

import com.lullaby.cardstudy.common.jpa.BaseEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Getter
@EntityListeners(AuditingEntityListener.class)
@NoArgsConstructor
@Entity
public class CardSet extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "card_set_id")
    private Long id;
    private String name;
    private String description;
    private LocalDateTime lastReviewedAt;
    private Integer reviewCount;
    private Integer totalCardCount;
    public CardSet(String name, String description) {
        this.name = name;
        this.description = description;
        this.lastReviewedAt = LocalDateTime.now();
        this.reviewCount = 0;
        this.totalCardCount = 0;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void review() {
        this.lastReviewedAt = LocalDateTime.now();
        this.reviewCount++;
    }

    public void addCard() {
        this.totalCardCount++;
    }

    public void deleteCard() {
        this.totalCardCount--;
    }

}
