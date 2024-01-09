package com.lullaby.cardstudy.domain.card;

import com.lullaby.cardstudy.common.jpa.BaseEntity;
import com.lullaby.cardstudy.domain.cardset.CardSet;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Getter
@Inheritance(strategy = InheritanceType.JOINED)
@DiscriminatorColumn
@NoArgsConstructor
@Entity
public abstract class Card extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "card_id")
    protected Long id;
    protected MemorizationLevel memorizationLevel;
    protected LocalDateTime nextReviewAt;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "card_set_id")
    protected CardSet cardSet;

    protected Card(CardSet cardSet) {
        setCardSet(cardSet);
        this.memorizationLevel = MemorizationLevel.Difficult;
        this.nextReviewAt = LocalDateTime.now();
    }

    public void setCardSet(CardSet cardSet) {
        if (cardSet == null) {
            throw new IllegalArgumentException("카드 셋을 입력해 주세요.");
        }
        this.cardSet = cardSet;
    }

    public void increaseMemorizationLevel() {
        this.memorizationLevel = this.memorizationLevel.getNextLevel();
        this.nextReviewAt = LocalDateTime.now().plus(this.memorizationLevel.getDuration());
    }

    public void decreaseMemorizationLevel() {
        this.memorizationLevel = this.memorizationLevel.getPreviousLevel();
        this.nextReviewAt = LocalDateTime.now().plus(this.memorizationLevel.getDuration());
    }

    public void clearMemorizationLevel() {
        this.memorizationLevel = MemorizationLevel.Difficult;
        this.nextReviewAt = LocalDateTime.now().plus(this.memorizationLevel.getDuration());
    }

}
