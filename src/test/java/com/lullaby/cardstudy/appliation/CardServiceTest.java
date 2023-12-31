package com.lullaby.cardstudy.appliation;

import com.lullaby.cardstudy.appliation.card.CardService;
import com.lullaby.cardstudy.appliation.cardset.dto.AddCardSetCommand;
import com.lullaby.cardstudy.appliation.cardset.CardSetService;
import com.lullaby.cardstudy.appliation.cardset.dto.CardSetResponse;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class CardServiceTest {

    @Autowired
    CardService cardService;

    @Autowired
    CardSetService cardSetService;

    @Test
    void name() {

        CardSetResponse cardSet = cardSetService.addCardSet(new AddCardSetCommand("test", null));

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
        cardService.addCardByFile(cardSet.id(), textContent);
    }
}
