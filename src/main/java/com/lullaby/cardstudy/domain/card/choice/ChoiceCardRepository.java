package com.lullaby.cardstudy.domain.card.choice;

import com.lullaby.cardstudy.domain.cardset.CardSet;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ChoiceCardRepository extends JpaRepository<ChoiceCard, Long> {
    List<ChoiceCard> findByCardSetOrderByIdDesc(CardSet cardSet);


}
