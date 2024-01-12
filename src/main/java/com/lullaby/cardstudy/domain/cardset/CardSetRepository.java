package com.lullaby.cardstudy.domain.cardset;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface CardSetRepository extends JpaRepository<CardSet, Long> {

    List<CardSet> findAllByOwnerId(Long memberId);

    Optional<CardSet> findByIdAndOwnerId(Long id, Long ownerId);

    @Query("select cs from CardSet cs " +
            "left join fetch cs.cards c " +
            "where cs.id = :id and c.id = :cardId and cs.owner.id = :ownerId")
    Optional<CardSet> findCard(@Param("id") Long id, @Param("cardId") Long cardId, @Param("ownerId") Long ownerId);

}
