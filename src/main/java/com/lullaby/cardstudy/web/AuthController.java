package com.lullaby.cardstudy.web;

import com.lullaby.cardstudy.dto.SignInResponse;
import com.lullaby.cardstudy.security.TokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/api/auth")
@RequiredArgsConstructor
@RestController
public class AuthController {

    private final TokenProvider tokenProvider;

    @PostMapping("/sign-in")
    public SignInResponse signIn() {
        String accessToken = tokenProvider.accessToken();
        return new SignInResponse(accessToken);
    }

}
