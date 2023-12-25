package com.lullaby.cardstudy.domain;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CardRepository extends JpaRepository<Card, Long> {
    List<Card> findByCardSet(CardSet cardSet);

}
