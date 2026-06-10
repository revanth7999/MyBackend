package com.backend.MyBackend.email.listeners;

import com.backend.MyBackend.email.events.VerificationEmailEvent;
import com.backend.MyBackend.email.service.EmailProvider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

@Component
public class VerificationEmailListener{

    @Value("${app.frontendUrl}")
    private String frontendUrl;

    private final EmailProvider emailProvider;
    private static final Logger log = LoggerFactory.getLogger(VerificationEmailListener.class);

    public VerificationEmailListener(
            EmailProvider emailProvider){
        this.emailProvider = emailProvider;
    }

    @Async
    @EventListener
    public void handleVerificationEmail(
            VerificationEmailEvent event){

        String to = event.email();
        String verificationUrl = frontendUrl +
                "/#/verify-email?token=" +
                event.token();

        String html = """
                <p>Click the link below to verify your email:</p>
                <a href="%s">Verify Email</a>
                """.formatted(verificationUrl);

        try{
            log.info("Sending an email to {}",to);
            emailProvider.sendEmail(
                    to,
                    "Verify your email",
                    html);

        } catch (Exception e){

            log.error(
                    "Failed to send verification email to {}",
                    event.email(),
                    e);

            // Optional: retry, save failed event, etc.
        }
    }
}
