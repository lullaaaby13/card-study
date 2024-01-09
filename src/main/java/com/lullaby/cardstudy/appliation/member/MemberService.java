package com.lullaby.cardstudy.appliation.member;

import com.lullaby.cardstudy.appliation.authenticate.PasswordEncryptService;
import com.lullaby.cardstudy.appliation.member.dto.CreateMemberRequest;
import com.lullaby.cardstudy.appliation.member.dto.MemberResponse;
import com.lullaby.cardstudy.domain.member.Member;
import com.lullaby.cardstudy.domain.member.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;

@RequiredArgsConstructor
@Service
public class MemberService {

    private final MemberRepository memberRepository;
    private final PasswordEncryptService passwordEncryptService;

    public MemberResponse one(Long id) {
        Member member = findEntityOrElseThrow(id);
        return new MemberResponse(member);
    }
    public MemberResponse create(CreateMemberRequest request) {
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

    public Member findEntityOrElseThrow(Long id) {
        return memberRepository.findById(id)
                .orElseThrow(() -> new HttpClientErrorException(HttpStatus.NOT_FOUND, "존재하지 않는 회원입니다."));
    }

}

