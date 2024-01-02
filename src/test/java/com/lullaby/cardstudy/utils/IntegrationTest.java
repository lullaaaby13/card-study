package com.lullaby.cardstudy.utils;

import com.lullaby.cardstudy.domain.CardRepository;
import com.lullaby.cardstudy.domain.CardSetRepository;
import com.lullaby.cardstudy.domain.MemberRepository;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class IntegrationTest {

    @Autowired
    protected MemberRepository memberRepository;
    @Autowired
    private CardRepository cardRepository;
    @Autowired
    private CardSetRepository cardSetRepository;

    @BeforeEach
    public void setup() {
        memberRepository.deleteAll();
        cardRepository.deleteAll();
        cardSetRepository.deleteAll();
    }

}
