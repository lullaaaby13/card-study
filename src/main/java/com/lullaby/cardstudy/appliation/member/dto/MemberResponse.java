package com.lullaby.cardstudy.appliation.member.dto;

import com.lullaby.cardstudy.domain.member.Member;

import java.time.LocalDateTime;

public record MemberResponse(
        Long id,
        String account,
        String nickname,
        String email,
        LocalDateTime createdAt,
        LocalDateTime updatedAt
) {
    public MemberResponse(Member member) {
        this(member.getId(),
                member.getAccount(),
                member.getNickname(),
                member.getEmail(),
                member.getCreatedAt(),
                member.getUpdatedAt()
        );
    }
}
