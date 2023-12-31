package com.lullaby.cardstudy.security;

import io.jsonwebtoken.Claims;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@RequiredArgsConstructor
@Service
public class AuthenticateService {

    private final TokenProvider tokenProvider;

    public Authentication authenticate(String bearerToken) {
        if (!tokenProvider.validateToken(bearerToken)) {
            throw new IllegalArgumentException();
        }
        Claims claims = tokenProvider.parseToken(bearerToken);
        return new UsernamePasswordAuthenticationToken("admin", null, new ArrayList<>());
    }
}
