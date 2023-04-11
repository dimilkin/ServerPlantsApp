package com.server.backend.security;

import com.server.backend.dto.AccountRegDto;
import com.server.backend.events.OnCreateAccountEvent;
import com.server.backend.exceptions.GlobalExceptionsHandler;
import com.server.backend.models.UserInfo;
import com.server.backend.models.UserRole;
import com.server.backend.services.UserInfoService;
import com.server.backend.services.UserRolesService;
import com.server.backend.services.VerificationTokenService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import javax.mail.SendFailedException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ThreadLocalRandom;

import static com.server.backend.security.Constants.AUTHORITY_USER;

@Component
public class UserRegistrationHandler {

    UserInfoService userInfoService;
    VerificationTokenService tokenService;
    ApplicationEventPublisher eventPublisher;
    UserRolesService rolesService;
    PasswordEncoder encoder;
    Map<String, UserInfo> usersWaitingActivation = new HashMap<>();
    private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionsHandler.class);

    public UserRegistrationHandler(UserInfoService userInfoService, VerificationTokenService tokenService, UserRolesService rolesService,
                                   ApplicationEventPublisher eventPublisher, PasswordEncoder encoder) {
        this.userInfoService = userInfoService;
        this.tokenService = tokenService;
        this.eventPublisher = eventPublisher;
        this.rolesService = rolesService;
        this.encoder = encoder;
    }

    public void startUserRegistration(AccountRegDto accountDto, String token) throws SendFailedException {

        UserInfo user = new UserInfo();
        user.setEnabled(false);
        UserRole role = rolesService.getByValue(AUTHORITY_USER);

        final String registrationCode = generateRegistrationCode();
        user.setUserRole(role);
        user.setPassword(encoder.encode(accountDto.getPassword()));
        user.setEmail(accountDto.getEmail());
        user.setVerificationCode(registrationCode);
        usersWaitingActivation.put(registrationCode, user);
        sendVerificationCode(user, token, registrationCode);
    }

    public boolean accountActivated(String email, String code){

        UserInfo user = usersWaitingActivation.get(code);
        if (user != null && user.getEmail().equals(email)) {
            user.setEnabled(true);
            UserInfo createdUser = userInfoService.create(user);
            logger.info("User was created with id: " + createdUser.getId() + " and email: " + createdUser.getEmail());
            return true;
        }
        return false;
    }


    private void sendVerificationCode(UserInfo user, String token, String registrationCode) {
        eventPublisher.publishEvent(new OnCreateAccountEvent(registrationCode, user, token));
    }

    private String generateRegistrationCode() {
        int lowerBound = 1001;
        int upperBound = 9999;
        int code = ThreadLocalRandom.current().nextInt(lowerBound, upperBound);
        return String.valueOf(code);
    }
}
