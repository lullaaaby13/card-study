package com.lullaby.cardstudy.domain.card;

import com.lullaby.cardstudy.domain.cardset.CardSet;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CardRepository extends JpaRepository<Card, Long> {
    List<Card> findByCardSetOrderByIdDesc(CardSet cardSet);


}
