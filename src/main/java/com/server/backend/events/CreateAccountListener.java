package com.server.backend.events;

import com.server.backend.models.UserInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Component;

@Component
public class CreateAccountListener implements ApplicationListener<OnCreateAccountEvent> {

    @Autowired
    private MailSender mailSender;

    @Override
    public void onApplicationEvent(OnCreateAccountEvent event) {
        this.confirmRegistration(event);
    }

    private void confirmRegistration(OnCreateAccountEvent event) {
        UserInfo user = event.getUser();

        String recipient = user.getEmail();
        String subject = "Registration Code";
        String registrationCode = "This is your registration code for your DRYP app : " + event.getRegistrationCode();
        String message = "You successfully registered your account!";

        SimpleMailMessage email = new SimpleMailMessage();
        email.setFrom("myplantapp@outlook.com");
        email.setTo(recipient);
        email.setSubject(subject);
        email.setText(message + "\n" + registrationCode);
        mailSender.send(email);

    }
}
