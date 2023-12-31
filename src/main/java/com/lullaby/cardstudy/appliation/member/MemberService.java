package com.lullaby.cardstudy.appliation.member;

import com.lullaby.cardstudy.appliation.authenticate.PasswordEncryptService;
import com.lullaby.cardstudy.appliation.member.dto.CreateMemberRequest;
import com.lullaby.cardstudy.domain.Member;
import com.lullaby.cardstudy.domain.MemberRepository;
import com.lullaby.cardstudy.appliation.member.dto.MemberResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class MemberService {

    private final MemberRepository memberRepository;
    private final PasswordEncryptService passwordEncryptService;


    public MemberResponse createMember(CreateMemberRequest request) {
        if (memberRepository.existsByAccount(request.account())) {
            throw new IllegalArgumentException("이미 존재하는 아이디입니다.");
        }

        if (memberRepository.existsByNickname(request.nickname())) {
            throw new IllegalArgumentException("이미 존재하는 닉네임입니다.");
        }

        Member member = new Member(
                request.account(),
                passwordEncryptService.encrypt(request.password()),
                request.nickname(),
                request.email()
        );

        return new MemberResponse(memberRepository.save(member));
    }


}

