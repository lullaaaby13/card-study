package com.lullaby.cardstudy.web;

import com.lullaby.cardstudy.appliation.member.dto.CreateMemberRequest;
import com.lullaby.cardstudy.appliation.member.MemberService;
import com.lullaby.cardstudy.appliation.member.dto.MemberResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/api/member")
@RequiredArgsConstructor
@RestController
public class MemberController {

    private final MemberService memberService;

    @PostMapping
    public MemberResponse createMember(@RequestBody CreateMemberRequest request) {
        return memberService.createMember(request);
    }


}
