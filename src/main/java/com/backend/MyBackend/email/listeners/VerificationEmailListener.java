package com.backend.MyBackend.email.listeners;

import com.backend.MyBackend.email.events.VerificationEmailEvent;
import com.backend.MyBackend.email.service.EmailProvider;
import com.backend.MyBackend.email.service.EmailTemplateService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

/**
 * Handles {@link VerificationEmailEvent} by sending an email containing the user's email verification link.
 *
 * <p>
 * Email delivery is performed asynchronously so that the caller is not blocked while waiting for the external email
 * provider.
 * </p>
 */
@Component
public class VerificationEmailListener{

    @Value("${app.frontendUrl}")
    private String frontendUrl;

    private final EmailProvider emailProvider;
    private final EmailTemplateService emailTemplateService;
    private static final Logger log = LoggerFactory.getLogger(VerificationEmailListener.class);
    private static final String SUBJECT = "Verify your email!";

    public VerificationEmailListener(
            EmailProvider emailProvider,EmailTemplateService emailTemplateService){
        this.emailProvider = emailProvider;
        this.emailTemplateService = emailTemplateService;
    }

    /**
     * Sends an email verification message to the user.
     *
     * @param event
     *            the verification email event containing the recipient's email address and verification token
     */
    @Async
    @EventListener
    public void handleVerificationEmail(
            VerificationEmailEvent event){

        String recipient = event.email();
        String verificationUrl = frontendUrl +
                "/#/verify-email?token=" +
                event.token();

        String html = emailTemplateService.verificationEmail(
                verificationUrl);

        try{
            log.info("Sending verification email to {}",recipient);
            emailProvider.sendEmail(
                    recipient,
                    SUBJECT,
                    html);
            log.info("Verification email sent to {}",recipient);
        } catch (Exception e){

            log.error(
                    "Failed to send verification email to {}",
                    event.email(),
                    e);

            // Optional: retry, save failed event, etc.
        }
    }
}
