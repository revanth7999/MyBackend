package com.backend.MyBackend.email.listeners;

import com.backend.MyBackend.account.event.UserRegisteredEvent;
import com.backend.MyBackend.email.service.EmailProvider;
import com.backend.MyBackend.email.service.EmailTemplateService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

/**
 * Handles {@link UserRegisteredEvent} by sending a welcome email to the newly registered user.
 *
 * <p>
 * Email delivery is performed asynchronously so that user registration is not delayed by external email provider
 * latency.
 * </p>
 */
@Component
public class WelcomeEmailListener{

    private static final Logger log = LoggerFactory.getLogger(WelcomeEmailListener.class);
    private static final String SUBJECT = "🎉 Welcome to DineMaster!";
    private final EmailProvider emailProvider;
    private final EmailTemplateService emailTemplateService;

    public WelcomeEmailListener(EmailProvider emailProvider,EmailTemplateService emailTemplateService){
        this.emailProvider = emailProvider;
        this.emailTemplateService = emailTemplateService;
    }

    /**
     * Sends a welcome email to the registered user.
     *
     * @param event
     *            the user registration event
     */
    @Async
    @EventListener
    public void handleWelcomeEmail(
            UserRegisteredEvent event){

        String recipient = event.email();
        String html = emailTemplateService.welcomeEmail(event.userName());

        try{
            log.info("Sending welcome email to {}",recipient);

            emailProvider.sendEmail(
                    recipient,
                    SUBJECT,
                    html);

            log.info("Successfully sent welcome email to {}",recipient);

        } catch (Exception ex){

            log.error(
                    "Failed to send welcome email to {}",
                    recipient,
                    ex);

            // TODO:
            // Publish WelcomeEmailFailedEvent
            // or retry using Spring Retry / RabbitMQ / Kafka
        }

    }
}
