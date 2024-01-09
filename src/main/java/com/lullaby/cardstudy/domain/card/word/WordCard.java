package com.lullaby.cardstudy.domain.card.word;

import com.lullaby.cardstudy.domain.card.Card;
import com.lullaby.cardstudy.domain.cardset.CardSet;
import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@Entity
public class WordCard extends Card {
    private String question;
    private String answer;

    public WordCard(CardSet cardSet, String question, String answer) {
        super(cardSet);
        setQuestion(question);
        setAnswer(answer);
    }

    public void setQuestion(String question) {
        if (question == null || question.isBlank()) {
            throw new IllegalArgumentException("질문을 입력해 주세요.");
        }
        this.question = question;
    }

    public void setAnswer(String answer) {
        if (answer == null || answer.isBlank()) {
            throw new IllegalArgumentException("답변을 입력해 주세요.");
        }
        this.answer = answer;
    }

}
