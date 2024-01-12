package com.lullaby.cardstudy.domain.cardset;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;


@DataJpaTest
class CardSetRepositoryTest {

    @Autowired
    CardSetRepository cardSetRepository;

    @Test
    void name() {
        Optional<CardSet> card = cardSetRepository.findCard(1L, 1L, 1L);
    }
}