package com.server.backend;

import com.server.backend.services.ValidationService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class ValidationServiceTest {

    @Autowired
    ValidationService validationService;

    @Test
    public void validationShouldReturnTrueWhenEmailIsNull (){
        Assertions.assertTrue(validationService.isEmailInvalid(null));
    }

    @Test
    public void emailValidationShouldReturnTrueWhenEmailIsBlank (){
        Assertions.assertTrue(validationService.isEmailInvalid(""));
    }

    @Test
    public void emailValidationShouldReturnTrueWhenEmailIsWrong (){
        Assertions.assertTrue(validationService.isEmailInvalid("jibirish"));
    }

    @Test
    public void validationShouldReturnTrueWhenValueIsBlank (){
        Assertions.assertTrue(validationService.isValueInvalid("    "));
    }

    @Test
    public void validationShouldReturnTrueWhenValueIsEmpty (){
        Assertions.assertTrue(validationService.isValueInvalid(""));
    }



}
