package com.lullaby.cardstudy.appliation.member;

import com.lullaby.cardstudy.appliation.authenticate.PasswordEncryptService;
import com.lullaby.cardstudy.appliation.member.dto.CreateMemberRequest;
import com.lullaby.cardstudy.appliation.member.dto.MemberResponse;
import com.lullaby.cardstudy.common.exception.NotFoundException;
import com.lullaby.cardstudy.domain.Member;
import com.lullaby.cardstudy.domain.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class MemberService {

    private final MemberRepository memberRepository;
    private final PasswordEncryptService passwordEncryptService;


    public MemberResponse getMemberResponse(Long id) {
        Member member = findMemberEntityOrElseThrow(id);
        return new MemberResponse(member);
    }
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

    public Member findMemberEntityOrElseThrow(Long id) {
        return memberRepository.findById(id).orElseThrow(() -> new NotFoundException("존재하지 않는 회원입니다."));
    }

}

