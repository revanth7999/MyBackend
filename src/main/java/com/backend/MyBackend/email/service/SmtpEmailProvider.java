package com.backend.MyBackend.email.service;

import org.springframework.context.annotation.Profile;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
@Profile("dev")
public class SmtpEmailProvider implements EmailProvider{

    private final JavaMailSender mailSender;

    public SmtpEmailProvider(JavaMailSender mailSender){
        this.mailSender = mailSender;
    }

    public void sendEmail(String to,String subject,String html){
        SimpleMailMessage msg = new SimpleMailMessage();
        msg.setTo(to);
        msg.setSubject(subject);
        msg.setText(html);
        mailSender.send(msg);
    }
}
