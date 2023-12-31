package com.lullaby.cardstudy.web;

import com.lullaby.cardstudy.appliation.authenticate.AuthenticateService;
import com.lullaby.cardstudy.appliation.authenticate.dto.SignInCommand;
import com.lullaby.cardstudy.appliation.authenticate.dto.SignInResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/api/auth")
@RequiredArgsConstructor
@RestController
public class AuthController {

    private final AuthenticateService authenticateService;

    @PostMapping("/sign-in")
    public SignInResponse signIn(@RequestBody SignInCommand request) {
        return authenticateService.signIn(request);
    }

}
