package com.backend.MyBackend.account.listener;

import com.backend.MyBackend.account.entity.User;
import com.backend.MyBackend.account.event.UserRegisteredEvent;
import com.backend.MyBackend.account.repository.UserRepository;
import com.backend.MyBackend.email.events.VerificationEmailEvent;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

/**
 * Handles {@link UserRegisteredEvent} by preparing the user's email verification workflow.
 *
 * <p>
 * This listener:
 * <ul>
 * <li>Generates an email verification token.</li>
 * <li>Persists the generated token and its expiry.</li>
 * <li>Publishes a {@link VerificationEmailEvent} to trigger the asynchronous email delivery.</li>
 * </ul>
 *
 * <p>
 * This listener is executed synchronously as part of the user registration workflow.
 * </p>
 */
@Component
public class VerificationRegistrationListener{

    private final UserRepository userRepository;
    private final ApplicationEventPublisher applicationEventPublisher;

    public VerificationRegistrationListener(UserRepository userRepository,
            ApplicationEventPublisher applicationEventPublisher){
        this.userRepository = userRepository;
        this.applicationEventPublisher = applicationEventPublisher;
    }

    /**
     * Generates an email verification token for the newly registered user and publishes a
     * {@link VerificationEmailEvent}.
     *
     * @param event
     *            the user registration event
     */
    @EventListener
    public void handle(UserRegisteredEvent event){

        User user = event.getUser();

        user.generateVerificationToken();
        userRepository.save(user);

        applicationEventPublisher.publishEvent(
                new VerificationEmailEvent(
                        user.getEmail(),
                        user.getEmailVerificationToken()));
    }
}
