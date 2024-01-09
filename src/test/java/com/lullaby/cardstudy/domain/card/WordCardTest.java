package com.lullaby.cardstudy.domain.card;

import com.lullaby.cardstudy.domain.card.word.WordCard;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static com.lullaby.cardstudy.fixture.CardSetFixture.WORD_CARDSET;
import static org.junit.jupiter.api.Assertions.assertEquals;

class WordCardTest {

    @DisplayName("카드를 생성 한다.")
    @Test
    void create() {
        WordCard card = new WordCard(WORD_CARDSET, "Apple", "사과");
        assertEquals("Apple", card.getQuestion());
        assertEquals("사과", card.getAnswer());
    }

    @DisplayName("카드의 질문을 수정 한다.")
    @Test
    void updateQuestion() {
        WordCard card = new WordCard(WORD_CARDSET, "Apple", "사과");
        card.setQuestion("Banana");
        assertEquals("Banana", card.getQuestion());
    }

    @DisplayName("카드의 정답을 수정 한다.")
    @Test
    void updgetAnswer() {
        WordCard card = new WordCard(WORD_CARDSET, "Apple", "사과");
        card.setAnswer("바나나");
        assertEquals("바나나", card.getAnswer());
    }

    @DisplayName("카드의 암기 레벨을 증가 한다.")
    @Test
    void increaseMemorizationLevel() {
        WordCard card = new WordCard(WORD_CARDSET, "Apple", "사과");
        card.increaseMemorizationLevel();
        assertEquals(MemorizationLevel.SomewhatDifficult, card.getMemorizationLevel());
    }

    @DisplayName("카드의 암기 레벨을 감소 한다.")
    @Test
    void decreaseMemorizationLevel() {
        WordCard card = new WordCard(WORD_CARDSET, "Apple", "사과");
        card.increaseMemorizationLevel();
        card.increaseMemorizationLevel();
        card.decreaseMemorizationLevel();
        assertEquals(MemorizationLevel.SomewhatDifficult, card.getMemorizationLevel());
    }

}
