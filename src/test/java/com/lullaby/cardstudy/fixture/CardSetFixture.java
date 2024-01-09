package com.lullaby.cardstudy.fixture;

import com.lullaby.cardstudy.domain.cardset.CardSet;
import com.lullaby.cardstudy.domain.cardset.CardSetType;

public class CardSetFixture {
    public static final CardSet WORD_CARDSET = new CardSet(CardSetType.WORD, "TOEIC", "영단기", MemberFixture.MEMBER);
    public static final CardSet CHOICE_CARDSET = new CardSet(CardSetType.CHOICE, "TOEIC Part5", "해커스", MemberFixture.MEMBER);
}
