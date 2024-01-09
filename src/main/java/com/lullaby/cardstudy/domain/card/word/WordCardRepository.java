package com.lullaby.cardstudy.domain.card.word;

import com.lullaby.cardstudy.domain.cardset.CardSet;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface WordCardRepository extends JpaRepository<WordCard, Long> {
    List<WordCard> findByCardSetOrderByIdDesc(CardSet cardSet);



}
