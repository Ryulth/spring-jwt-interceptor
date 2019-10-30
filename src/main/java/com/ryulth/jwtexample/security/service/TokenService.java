package com.ryulth.jwtexample.security.service;

import com.ryulth.jwtexample.security.dto.TokenDto;
import com.ryulth.jwtexample.security.jwt.TokenProvider;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class TokenService {
    private final TokenProvider tokenProvider;

    public TokenService(TokenProvider tokenProvider) {
        this.tokenProvider = tokenProvider;
    }

    public TokenDto publishToken(String userEmail) {
        String accessToken = tokenProvider.publishToken(userEmail, true);
        String refreshToken = tokenProvider.publishToken(userEmail, false);
        return TokenDto.builder().accessToken(accessToken).refreshToken(refreshToken).build();
    }
}
