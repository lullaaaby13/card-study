package com.lullaby.cardstudy.appliation.authenticate;

import com.lullaby.cardstudy.appliation.authenticate.dto.SignInCommand;
import com.lullaby.cardstudy.domain.Member;
import com.lullaby.cardstudy.domain.MemberRepository;
import com.lullaby.cardstudy.appliation.authenticate.dto.SignInResponse;
import com.lullaby.cardstudy.appliation.authenticate.dto.AuthenticatedUser;
import io.jsonwebtoken.Claims;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatusCode;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;

import java.util.ArrayList;

@RequiredArgsConstructor
@Service
public class AuthenticateService {

    private final MemberRepository memberRepository;
    private final PasswordEncryptService passwordEncryptService;
    private final TokenProvider tokenProvider;

    public SignInResponse signIn(SignInCommand request) {

        Member member = memberRepository.findByAccount(request.account())
                .orElseThrow(() -> new HttpClientErrorException(HttpStatusCode.valueOf(401), "존재하지 않는 계정이거나 비밀번호가 올바르지 않습니다."));

        if (!passwordEncryptService.match(request.password(), member.getPassword())) {
            throw new HttpClientErrorException(HttpStatusCode.valueOf(401), "존재하지 않는 계정이거나 비밀번호가 올바르지 않습니다.");
        }

        String accessToken = tokenProvider.accessToken(member.getId());
        String refreshToken = tokenProvider.refreshToken(member.getId());

        return new SignInResponse(accessToken, refreshToken);
    }

    public Authentication authenticate(String bearerToken) {
        if (!tokenProvider.validateToken(bearerToken)) {
            throw new IllegalArgumentException();
        }
        Claims claims = tokenProvider.parseToken(bearerToken);
        Long userId = Long.valueOf(claims.getSubject());

        if (!memberRepository.existsById(userId)) {
            throw new HttpClientErrorException(HttpStatusCode.valueOf(401), "존재하지 않는 계정이거나 비밀번호가 올바르지 않습니다.");
        }

        AuthenticatedUser authenticatedUser = new AuthenticatedUser(userId);
        return new UsernamePasswordAuthenticationToken(authenticatedUser, null, new ArrayList<>());
    }

}
