package com.backend.MyBackend.email.service;

import com.resend.Resend;
import com.resend.services.emails.model.CreateEmailOptions;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

@Service
@Profile("prod")
public class ResendEmailProvider implements EmailProvider{

    @Value("${app.resend.api}")
    private String apiKey;

    public void sendEmail(String to,String subject,String html){
        Resend resend = new Resend(apiKey);

        CreateEmailOptions options = CreateEmailOptions.builder()
                .from("Acme <onboarding@resend.dev>")
                .to(to)
                .subject(subject)
                .html(html)
                .build();

        try{
            resend.emails().send(options);
        } catch (Exception e){
            e.printStackTrace(); // IMPORTANT
            throw new RuntimeException("Email failed: " + e.getMessage());
        }
    }
}
