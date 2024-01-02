package com.lullaby.cardstudy.domain;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CardSetRepository extends JpaRepository<CardSet, Long> {

    List<CardSet> findAllByOwnerId(Long memberId);

    Optional<CardSet> findByIdAndOwnerId(Long id, Long ownerId);
}
