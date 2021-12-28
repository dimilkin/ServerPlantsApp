package com.server.backend.security;

import com.server.backend.dto.AccountRegDto;
import com.server.backend.events.OnCreateAccountEvent;
import com.server.backend.models.Token;
import com.server.backend.models.UserInfo;
import com.server.backend.models.UserRole;
import com.server.backend.services.UserInfoService;
import com.server.backend.services.UserRolesService;
import com.server.backend.services.VerificationTokenService;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;

import static com.server.backend.security.Constants.AUTHORITY_USER;

@Component
public class UserRegistrationHandler {

    UserInfoService userInfoService;
    VerificationTokenService tokenService;
    ApplicationEventPublisher eventPublisher;
    UserRolesService rolesService;
    PasswordEncoder encoder;

    public UserRegistrationHandler(UserInfoService userInfoService, VerificationTokenService tokenService, UserRolesService rolesService,
                                   ApplicationEventPublisher eventPublisher, PasswordEncoder encoder) {
        this.userInfoService = userInfoService;
        this.tokenService = tokenService;
        this.eventPublisher = eventPublisher;
        this.rolesService = rolesService;
        this.encoder = encoder;
    }

    public void startUserRegistration(AccountRegDto accountDto, String token, HttpServletRequest request) {

        UserInfo user = new UserInfo();
        user.setEnabled(true);
        UserRole role = rolesService.getByValue(AUTHORITY_USER);
        user.setUserRole(role);
        user.setPassword(encoder.encode(accountDto.getPassword()));
        user.setEmail(accountDto.getEmail());

        userInfoService.create(user);
        tokenService.saveToken(token, user);
        final String appUrl = "http://" + request.getServerName() + ":" + request.getServerPort() + ":" + request.getContextPath();
//        sendVerificationEmail(user, token, appUrl);
    }

    public void finishUserRegistration(String token){
        Token verificationToken = tokenService.findByToken(token);
        UserInfo user = verificationToken.getUser();
        user.setEnabled(true);
        userInfoService.update(user);
    }


    private void sendVerificationEmail(UserInfo user, String token, String appURL) {
        eventPublisher.publishEvent(new OnCreateAccountEvent(appURL, user, token));
    }
}
