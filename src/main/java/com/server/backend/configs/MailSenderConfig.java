package com.server.backend.configs;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.MailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;

import java.util.Properties;

@Configuration
public class MailSenderConfig {

    @Bean
    public MailSender javaMailService() {
        JavaMailSenderImpl javaMailSender = new JavaMailSenderImpl();
        javaMailSender.setHost("smtp-mail.outlook.com");
        javaMailSender.setUsername("myplantapp@outlook.com");
        javaMailSender.setPassword("MyPa$$word");
        javaMailSender.setPort(587);
        javaMailSender.setProtocol("smtp");
        Properties mailProperties = new Properties();
        mailProperties.put("mail.smtp.starttls.enable", "true");
        mailProperties.put("mail.smtp.debug", "true");
        javaMailSender.setJavaMailProperties(mailProperties);
        return javaMailSender;
    }
}