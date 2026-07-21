package com.backend.MyBackend.email.service;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.context.annotation.Profile;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

@Service
@Profile("dev")
public class SmtpEmailProvider implements EmailProvider{

    private final JavaMailSender mailSender;

    public SmtpEmailProvider(JavaMailSender mailSender){
        this.mailSender = mailSender;
    }

    @Override
    public void sendEmail(String to,String subject,String html){

        try{
            MimeMessage message = mailSender.createMimeMessage();

            MimeMessageHelper helper = new MimeMessageHelper(message,true,"UTF-8");

            helper.setTo(to);
            helper.setSubject(subject);
            helper.setText(html,true); // true = HTML

            mailSender.send(message);

        } catch (MessagingException e){
            throw new RuntimeException("Failed to send email",e);
        }
    }
}
