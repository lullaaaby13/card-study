package com.lullaby.cardstudy.domain;

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
public class Card {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "card_id")
    private Long id;
    private String front;
    private String back;
    private MemorizationLevel memorizationLevel;
    private LocalDateTime nextReviewAt;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id")
    private Category category;

    @CreatedDate
    private LocalDateTime createdAt;
    @LastModifiedDate
    private LocalDateTime updatedAt;

    public Card(Category category, String front, String back) {
        this.category = category;
        this.front = front;
        this.back = back;
        this.memorizationLevel = MemorizationLevel.Difficult;
        this.nextReviewAt = LocalDateTime.now();
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
    }

    public void updateFront(String front) {
        this.front = front;
    }

    public void updateBack(String back) {
        this.back = back;
    }

    public void increaseMemorizationLevel() {
        this.memorizationLevel = this.memorizationLevel.getNextLevel();
        this.nextReviewAt = LocalDateTime.now().plus(this.memorizationLevel.getDuration());
    }

    public void decreaseMemorizationLevel() {
        this.memorizationLevel = this.memorizationLevel.getPreviousLevel();
        this.nextReviewAt = LocalDateTime.now().plus(this.memorizationLevel.getDuration());
    }

}
