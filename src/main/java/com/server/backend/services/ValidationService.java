package com.server.backend.services;

import org.springframework.stereotype.Service;

@Service
public class ValidationService {

    public boolean isEmailInvalid(String providedEmail) {
        return null == providedEmail || providedEmail.isBlank() || providedEmail.isEmpty() || !providedEmail.matches("^\\S+@\\S+\\.\\S+$");
    }

    public boolean isValueInvalid(String providedValue){
        return providedValue.isBlank() || providedValue.isEmpty();
    }
}
