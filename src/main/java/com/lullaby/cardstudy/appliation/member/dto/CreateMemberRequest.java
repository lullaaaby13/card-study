package com.lullaby.cardstudy.appliation.member.dto;

public record CreateMemberRequest(
        String account,
        String password,
        String nickname,
        String email
) {
}
