package com.lullaby.cardstudy.appliation;

import com.lullaby.cardstudy.appliation.card.CardService;
import com.lullaby.cardstudy.appliation.cardset.CardSetService;
import com.lullaby.cardstudy.appliation.cardset.dto.AddCardSetCommand;
import com.lullaby.cardstudy.appliation.cardset.dto.CardSetResponse;
import com.lullaby.cardstudy.domain.cardset.CardSetType;
import com.lullaby.cardstudy.domain.member.Member;
import com.lullaby.cardstudy.fixture.MemberFixture;
import com.lullaby.cardstudy.utils.IntegrationTest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

class CardServiceTest extends IntegrationTest {

    @Autowired
    CardService cardService;

    @Autowired
    CardSetService cardSetService;

    Member member = MemberFixture.MEMBER;

    @BeforeEach
    void setUp() {
        memberRepository.save(member);
    }

    @Test
    void name() {

        CardSetResponse cardSet = cardSetService.addCardSet(member.getId(), new AddCardSetCommand(CardSetType.WORD, "test", null));

        String textContent = """
                Apple
                사과
                @@
                Banana
                바나나
                @@
                Citron
                유자
                """;
        cardService.addCardByFile(member.getId(), cardSet.id(), textContent);
    }
}
