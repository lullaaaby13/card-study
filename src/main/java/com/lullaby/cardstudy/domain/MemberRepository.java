package com.lullaby.cardstudy.domain;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Long> {

    Optional<Member> findByAccount(String account);
    boolean existsByAccount(String account);
    boolean existsByNickname(String nickname);
}
