package com.server.backend.repos;

import com.server.backend.models.Token;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface TokensRepo extends JpaRepository<Token, Long> {

    @Query("SELECT token FROM Token token WHERE token.tokenValue like %?1")
    Token findByTokenValue(String token);
}
