package com.server.backend.events;

import com.server.backend.models.UserInfo;
import org.springframework.context.ApplicationEvent;

public class OnCreateAccountEvent extends ApplicationEvent {

    private final String registrationCode;
    private final UserInfo user;
    private final String token;

    public OnCreateAccountEvent( String registrationCode, UserInfo user,  String token) {
        super(user);
        this.registrationCode = registrationCode;
        this.user = user;
        this.token = token;
    }

    public String getRegistrationCode() {
        return registrationCode;
    }
    public UserInfo getUser() {
        return user;
    }
    public String getToken() {
        return token;
    }
}
