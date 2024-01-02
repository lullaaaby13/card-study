package com.lullaby.cardstudy.security;

import com.lullaby.cardstudy.appliation.authenticate.AuthenticateService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

@EnableWebSecurity
@RequiredArgsConstructor
@Configuration
public class SecurityConfig {

    private final AuthenticateService authenticateService;
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        return httpSecurity
                .csrf(it -> it.disable())
                .cors(it -> it.configurationSource(corsConfigurationSource()))
                .authorizeRequests(authorizeRequests -> {
                    authorizeRequests
                            .requestMatchers(new AntPathRequestMatcher("/api/auth/**")).permitAll()
                            .requestMatchers(new AntPathRequestMatcher("/api/member", "POST")).permitAll()
                            .anyRequest().authenticated();
                })
                .addFilterBefore(new JwtFilter(authenticateService), UsernamePasswordAuthenticationFilter.class)
                .build();
    }

    // CORS 설정을 위한 빈 등록
    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.addAllowedOriginPattern("*"); // 허용할 도메인 (모든 도메인을 허용하려면 "*")
        configuration.addAllowedMethod("*"); // 허용할 HTTP 메서드
        configuration.addAllowedHeader("*"); // 허용할 헤더
        configuration.setAllowCredentials(true); // 이 부분을 추가
        configuration.setMaxAge(3600L); // 캐시 시간 (3600 == 1시간)
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }

}
