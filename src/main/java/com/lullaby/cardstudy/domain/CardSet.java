package com.lullaby.cardstudy.domain;

import com.lullaby.cardstudy.common.jpa.BaseEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

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
    @Enumerated(EnumType.STRING)
    private CardSetType type;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "owner_id")
    private Member owner;

    @OneToMany(mappedBy = "cardSet", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Card> cards = new ArrayList<>();

    public CardSet(CardSetType type, String name, String description, Member owner) {
        setType(type);
        setName(name);
        setDescription(description);
        this.lastReviewedAt = LocalDateTime.now();
        this.reviewCount = 0;
        this.totalCardCount = 0;
        this.owner = owner;
    }

    private void setType(CardSetType type) {
        if (Objects.isNull(type)) {
            throw new IllegalArgumentException("카드셋 타입을 입력해 주세요.");
        }
        this.type = type;
    }

    public void setName(String name) {
        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException("이름을 입력해 주세요.");
        }
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

    public Integer getToStudyCardCount() {
        long count = this.cards.stream()
                .filter(card -> card.getNextReviewAt().isBefore(LocalDateTime.now()))
                .count();
        return (int) count;
    }

    public boolean isOwnedBy(Member member) {
        return this.owner.equals(member);
    }

}
