package com.lullaby.cardstudy.domain.card;


import com.lullaby.cardstudy.domain.card.choice.ChoiceCard;
import com.lullaby.cardstudy.fixture.CardSetFixture;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@DisplayName("선다형 카드")
public class ChoiceCardTest {

    @DisplayName("카드를 생성 한다.")
    @Test
    void create() {
         ChoiceCard choiceCard = new ChoiceCard(CardSetFixture.CHOICE_CARDSET, "Apple", "사과");
    }

    @DisplayName("카드의 질문을 수정 한다.")
    @Test
    void updateQuestion() {
        ChoiceCard choiceCard = new ChoiceCard(CardSetFixture.CHOICE_CARDSET, "Apple", "사과");
        choiceCard.setQuestion("Banana");
        assertThat(choiceCard.getQuestion()).isEqualTo("Banana");
    }

    @DisplayName("카드의 정답을 수정 한다.")
    @Test
    void updateAnswer() {
        ChoiceCard choiceCard = new ChoiceCard(CardSetFixture.CHOICE_CARDSET, "Apple", "사과");
        choiceCard.setAnswer("바나나");
        assertThat(choiceCard.getAnswer()).isEqualTo("바나나");
    }

    @DisplayName("카드의 암기 레벨을 증가 한다.")
    @Test
    void increaseMemorizationLevel() {
        ChoiceCard choiceCard = new ChoiceCard(CardSetFixture.CHOICE_CARDSET, "Apple", "사과");
        choiceCard.increaseMemorizationLevel();
        assertThat(choiceCard.getMemorizationLevel()).isEqualTo(MemorizationLevel.SomewhatDifficult);
    }

    @DisplayName("카드의 암기 레벨을 감소 한다.")
    @Test
    void decreaseMemorizationLevel() {
        ChoiceCard choiceCard = new ChoiceCard(CardSetFixture.CHOICE_CARDSET, "Apple", "사과");
        choiceCard.increaseMemorizationLevel();
        choiceCard.increaseMemorizationLevel();
        choiceCard.decreaseMemorizationLevel();
        assertThat(choiceCard.getMemorizationLevel()).isEqualTo(MemorizationLevel.SomewhatDifficult);
    }

    @DisplayName("같은 순서의 선택지를 추가할 경우 선택지 추가에 실패 한다.")
    @Test
    void addChoice() {
        ChoiceCard choiceCard = new ChoiceCard(CardSetFixture.CHOICE_CARDSET, "다음중 가장 맛있는 과일은?", "나는 바나나를 가장 좋아합니다.");
        choiceCard.addChoice(1, "딸기", true);
        assertThatThrownBy(() -> choiceCard.addChoice(1, "사과", false))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("같은 순서의 선택지가 이미 존재합니다.");
    }

    @DisplayName("선택지를 수정 한다.")
    @Test
    void updateChoice() {
        // given
        ChoiceCard choiceCard = new ChoiceCard(CardSetFixture.CHOICE_CARDSET, "다음중 가장 맛있는 과일은?", "나는 바나나를 가장 좋아합니다.");
        choiceCard.addChoice(1, "딸기", true);

        // when
        choiceCard.updateChoice(1, "사과", false);

        // then
        assertThat(choiceCard.getChoices().get(0).getContent()).isEqualTo("사과");
        assertThat(choiceCard.getChoices().get(0).getIsAnswer()).isEqualTo(false);
    }

    @DisplayName("선택지 순서를 변경 한다.")
    @Test
    void changeChoiceSequence() {
        // given
        ChoiceCard choiceCard = new ChoiceCard(CardSetFixture.CHOICE_CARDSET, "다음중 가장 맛있는 과일은?", "나는 바나나를 가장 좋아합니다.");
        choiceCard.addChoice(1, "딸기", true);
        choiceCard.addChoice(2, "사과", false);
        choiceCard.addChoice(3, "바나나", false);

        // when
        choiceCard.changeChoiceSequence(1, 3);

        // then
        assertThat(choiceCard.getChoices().get(0).getContent()).isEqualTo("바나나");
        assertThat(choiceCard.getChoices().get(1).getContent()).isEqualTo("사과");
        assertThat(choiceCard.getChoices().get(2).getContent()).isEqualTo("딸기");
    }

}
