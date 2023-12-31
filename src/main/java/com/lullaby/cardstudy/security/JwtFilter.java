package com.lullaby.cardstudy.security;

import io.micrometer.common.util.StringUtils;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

public class JwtFilter extends OncePerRequestFilter {
    private final String  AUTHORIZATION_HEADER = "Authorization";
    private final String  BEARER_TOKEN_PREFIX = "Bearer ";
    private final int BEARER_TOKEN_LENGTH = BEARER_TOKEN_PREFIX.length();

    private final AuthenticateService authenticateService;

    public JwtFilter(AuthenticateService authenticateService) {
        this.authenticateService = authenticateService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String bearerToken = resolveToken(request);

        if (StringUtils.isNotBlank(bearerToken)) {
            Authentication authentication = authenticateService.authenticate(bearerToken);
            SecurityContextHolder.getContext().setAuthentication(authentication);
        }

        filterChain.doFilter(request, response);
    }

    private String resolveToken(HttpServletRequest request) {
        String bearerToken = request.getHeader(AUTHORIZATION_HEADER);
        if (StringUtils.isBlank(bearerToken)) {
            return null;
        }
        if (!bearerToken.startsWith(BEARER_TOKEN_PREFIX)) {
            return null;
        }
        return bearerToken.substring(BEARER_TOKEN_LENGTH);
    }
}
