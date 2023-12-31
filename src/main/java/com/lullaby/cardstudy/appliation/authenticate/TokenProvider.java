package com.lullaby.cardstudy.appliation.authenticate;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;


@Component
public class TokenProvider {

    private final Long ACCESS_TOKEN_EXPIRES_IN_MILLIS = 1000 * 60 * 60L;
    private final Long REFRESH_TOKEN_EXPIRES_IN_MILLIS = 1000 * 60 * 60 * 24 * 14L;
    private final Key jwtSecretKey;

    public TokenProvider() {
        byte[] keyBytes = Decoders.BASE64.decode("Y2hvcHBhLWRvbnQtYml0ZS1tZS1zcHJpbmctYm9vdC1qd3QtdGVzdC1zZWNyZXQta2V5LWNob3BwYS1kb250LWJpdGUtbWUtc3ByaW5nLWJvb3Qtand0LXRlc3Qtc2VjcmV0LWtleQo=");
        this.jwtSecretKey = Keys.hmacShaKeyFor(keyBytes);
    }

    public String accessToken(Long userId) {

        long now = new Date().getTime();
        long expired = now + ACCESS_TOKEN_EXPIRES_IN_MILLIS;

        return Jwts.builder()
                .setSubject(userId.toString())
                .setIssuedAt(new Date(now))
                .setExpiration(new Date(expired))
                .signWith(jwtSecretKey)
                .compact();
    }

    public String refreshToken(Long userId) {
        long now = new Date().getTime();
        long expired = now + REFRESH_TOKEN_EXPIRES_IN_MILLIS;

        return Jwts.builder()
                .setSubject(userId.toString())
                .setIssuedAt(new Date(now))
                .setExpiration(new Date(expired))
                .signWith(jwtSecretKey)
                .compact();
    }

    public Claims parseToken(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(jwtSecretKey)
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    public boolean validateToken(String token) {
        try {
            Jwts.parserBuilder().setSigningKey(jwtSecretKey).build().parseClaimsJws(token);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

}
