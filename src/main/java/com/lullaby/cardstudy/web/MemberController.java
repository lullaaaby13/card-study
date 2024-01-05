package com.lullaby.cardstudy.web;

import com.lullaby.cardstudy.appliation.authenticate.dto.AuthenticatedUser;
import com.lullaby.cardstudy.appliation.member.MemberService;
import com.lullaby.cardstudy.appliation.member.dto.CreateMemberRequest;
import com.lullaby.cardstudy.appliation.member.dto.MemberResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/api/member")
@RequiredArgsConstructor
@RestController
public class MemberController {

    private final MemberService memberService;

    @PostMapping
    public MemberResponse createMember(@RequestBody CreateMemberRequest request) {
        return memberService.createMember(request);
    }

    @GetMapping("current")
    public MemberResponse currentMember(@AuthenticationPrincipal AuthenticatedUser authenticatedUser) {
        return memberService.getMemberResponse(authenticatedUser.getUserId());
    }

}
