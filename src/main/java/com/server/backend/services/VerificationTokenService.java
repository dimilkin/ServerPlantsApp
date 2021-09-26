package com.server.backend.services;

import com.server.backend.models.Token;
import com.server.backend.models.UserInfo;

public interface VerificationTokenService {

    Token findByToken(String token);

    void saveToken(String token, UserInfo user);

    void delete(Token token);

}
