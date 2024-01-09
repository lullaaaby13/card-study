package com.lullaby.cardstudy.utils;

import com.lullaby.cardstudy.domain.card.choice.ChoiceCardRepository;
import com.lullaby.cardstudy.domain.card.word.WordCardRepository;
import com.lullaby.cardstudy.domain.cardset.CardSetRepository;
import com.lullaby.cardstudy.domain.member.MemberRepository;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class IntegrationTest {

    @Autowired
    protected MemberRepository memberRepository;
    @Autowired
    private WordCardRepository wordCardRepository;
    @Autowired
    private ChoiceCardRepository choiceCardRepository;
    @Autowired
    private CardSetRepository cardSetRepository;

    @BeforeEach
    public void setup() {
        cardSetRepository.deleteAll();
        wordCardRepository.deleteAll();
        choiceCardRepository.deleteAll();
        memberRepository.deleteAll();
    }

}
