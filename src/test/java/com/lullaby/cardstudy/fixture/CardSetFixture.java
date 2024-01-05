package com.lullaby.cardstudy.fixture;

import com.lullaby.cardstudy.domain.cardset.CardSet;
import com.lullaby.cardstudy.domain.cardset.CardSetType;

public class CardSetFixture {
    public static final CardSet CARDSET = new CardSet(CardSetType.WORD, "TOEIC", "영단기", MemberFixture.MEMBER);
}
