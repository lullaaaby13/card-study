package com.lullaby.cardstudy.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CardTest {

    @DisplayName("카드를 생성 한다.")
    @Test
    void create() {
        Card card = new Card(null, "Apple", "사과");
        assertEquals("Apple", card.getFront());
        assertEquals("사과", card.getBack());
    }

    @DisplayName("카드의 앞면을 수정 한다.")
    @Test
    void updateFront() {
        Card card = new Card(null, "Apple", "사과");
        card.setFront("Banana");
        assertEquals("Banana", card.getFront());
    }

    @DisplayName("카드의 뒷면을 수정 한다.")
    @Test
    void updateBack() {
        Card card = new Card(null, "Apple", "사과");
        card.setBack("바나나");
        assertEquals("바나나", card.getBack());
    }

    @DisplayName("카드의 암기 레벨을 증가 한다.")
    @Test
    void increaseMemorizationLevel() {
        Card card = new Card(null, "Apple", "사과");
        card.increaseMemorizationLevel();
        assertEquals(MemorizationLevel.SomewhatDifficult, card.getMemorizationLevel());
    }

    @DisplayName("카드의 암기 레벨을 감소 한다.")
    @Test
    void decreaseMemorizationLevel() {
        Card card = new Card(null, "Apple", "사과");
        card.increaseMemorizationLevel();
        card.increaseMemorizationLevel();
        card.decreaseMemorizationLevel();
        assertEquals(MemorizationLevel.SomewhatDifficult, card.getMemorizationLevel());
    }

}
