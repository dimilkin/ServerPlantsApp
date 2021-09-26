package com.server.backend.services.impl;

import com.server.backend.models.Token;
import com.server.backend.models.UserInfo;
import com.server.backend.repos.TokensRepo;
import com.server.backend.services.VerificationTokenService;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.time.temporal.TemporalUnit;

@Service
public class VerificationTokenServiceImpl implements VerificationTokenService {

    private TokensRepo repository;

    public VerificationTokenServiceImpl(TokensRepo repository) {
        this.repository = repository;
    }

    @Override
    public Token findByToken(String tokenValue) {
        return repository.findByTokenValue(tokenValue);
    }

    @Override
    public void saveToken(String tokenValue, UserInfo user) {
        Token token = new Token();
        token.setTokenValue(tokenValue);
        token.setUser(user);
        LocalDateTime expiryDate = LocalDateTime.now().plus(12, ChronoUnit.HOURS);
//        token.setExpiryDate(expiryDate);
        repository.save(token);
    }

    @Override
    public void delete(Token token) {
        repository.delete(token);
    }
}
