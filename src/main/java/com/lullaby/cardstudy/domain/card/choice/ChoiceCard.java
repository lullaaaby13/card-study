package com.lullaby.cardstudy.domain.card.choice;

import com.lullaby.cardstudy.domain.card.Card;
import com.lullaby.cardstudy.domain.cardset.CardSet;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@Getter
@NoArgsConstructor
@Entity
public class ChoiceCard extends Card {
    private String question;
    private String answer;

    @OneToMany(mappedBy = "choiceCard", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Choice> choices = new ArrayList<>();

    public ChoiceCard(CardSet cardSet, String question, String answer) {
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

    public void addChoice(Integer sequence, String content, Boolean isAnswer) {
        Choice choice = new Choice(this, sequence, content, isAnswer);
        boolean hasSameSequence = choices.stream().anyMatch(c -> c.getSequence().equals(sequence));
        if (hasSameSequence) {
            throw new IllegalArgumentException("같은 순서의 선택지가 이미 존재합니다.");
        }
        choices.add(choice);
    }

    public void updateChoice(Integer sequence, String content, Boolean isAnswer) {
        Choice choice = choices.stream()
                .filter(c -> c.getSequence().equals(sequence))
                .findFirst().orElseThrow(() -> new IllegalArgumentException("존재하지 않는 선택지 입니다."));
        choice.setContent(content);
        choice.setIsAnswer(isAnswer);
    }

    public void changeChoiceSequence(Integer sequence, Integer newSequence) {
        Choice choice = choices.stream()
                .filter(c -> c.getSequence().equals(sequence))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 선택지 입니다."));

        choices.stream()
                .filter(c -> c.getSequence().equals(newSequence))
                .findFirst()
                .ifPresent(it -> it.setSequence(sequence));

        choice.setSequence(newSequence);

        choices.sort(Comparator.comparing(Choice::getSequence));
    }


}
