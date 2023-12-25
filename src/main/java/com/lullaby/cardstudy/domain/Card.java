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
public class Card extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "card_id")
    private Long id;
    private String front;
    private String back;
    private MemorizationLevel memorizationLevel;
    private LocalDateTime nextReviewAt;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "card_set_id")
    private CardSet cardSet;

    public Card(CardSet cardSet, String front, String back) {
        this.cardSet = cardSet;
        this.front = front;
        this.back = back;
        this.memorizationLevel = MemorizationLevel.Difficult;
        this.nextReviewAt = LocalDateTime.now();
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
