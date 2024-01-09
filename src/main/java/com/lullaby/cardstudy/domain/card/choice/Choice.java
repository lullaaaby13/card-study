package com.lullaby.cardstudy.domain.card.choice;

import com.lullaby.cardstudy.common.jpa.BaseEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@Entity
public class Choice extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "choice_id")
    private Long id;

    private Integer sequence;
    private String content;
    private Boolean isAnswer;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "card_id")
    private ChoiceCard choiceCard;

    public Choice(ChoiceCard choiceCard, Integer sequence, String content, Boolean isAnswer) {
        setSequence(sequence);
        setContent(content);
        setIsAnswer(isAnswer);
        this.choiceCard = choiceCard;
    }

    public void setSequence(Integer order) {
        if (order == null || order < 1) {
            throw new IllegalArgumentException("선택지 순서를 입력해 주세요.");
        }
        this.sequence = order;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public void setIsAnswer(Boolean isAnswer) {
        if (isAnswer == null) {
            throw new IllegalArgumentException("정답 여부를 입력해 주세요.");
        }
        this.isAnswer = isAnswer;
    }

}
