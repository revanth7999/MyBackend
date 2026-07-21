package com.backend.MyBackend.email.service;

import com.backend.MyBackend.account.entity.User;
import com.backend.MyBackend.account.repository.UserRepository;
import com.backend.MyBackend.common.constants.Constants;
import com.backend.MyBackend.email.constants.EmailConstants;
import com.backend.MyBackend.email.events.VerificationEmailEvent;
import com.backend.MyBackend.email.exceptions.EmailAlreadyVerifiedException;
import com.backend.MyBackend.email.exceptions.EmailNotFoundException;
import com.backend.MyBackend.email.exceptions.InvalidVerificationTokenException;
import com.backend.MyBackend.email.exceptions.VerificationTokenExpiredException;
import com.backend.MyBackend.exception.UserNotFoundException;
import com.backend.MyBackend.notification.repository.NotificationRepository;
import com.backend.MyBackend.notification.service.NotificationService;
import java.sql.Timestamp;
import java.time.Instant;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

@Service
public class EmailService{

    @Value("${app.frontendUrl}")
    private String frontendUrl;

    @Value("${app.resend.api}")
    private String resendApi;

    @Value("${app.environment}")
    private String environment;

    private final UserRepository userRepository;
    private final ApplicationEventPublisher applicationEventPublisher;
    private final NotificationService notificationService;

    public EmailService(UserRepository userRepository,
            ApplicationEventPublisher applicationEventPublisher,NotificationRepository notificationRepository,
            NotificationService notificationService,NotificationService notificationService1){
        this.userRepository = userRepository;
        this.applicationEventPublisher = applicationEventPublisher;

        this.notificationService = notificationService1;
    }

    public void sendVerificationEmail(Long id){
        User user = userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException(Constants.USER_NOT_FOUND));

        if (user.getEmail() == null || user.getEmail().isBlank()){
            throw new EmailNotFoundException(Constants.USER_EMAIL_EMPTY);
        }
        user.generateVerificationToken();

        userRepository.save(user);
        applicationEventPublisher.publishEvent(
                new VerificationEmailEvent(
                        user.getEmail(),
                        user.getEmailVerificationToken()));
    }

    public void verifyEmail(String token){
        User user = userRepository.findByEmailVerificationToken(token);

        if (user == null){
            throw new InvalidVerificationTokenException(
                    EmailConstants.INVALID_VERIFICATION_TOKEN);
        }

        if (user.getIsEmailVerified()){
            throw new EmailAlreadyVerifiedException(
                    EmailConstants.EMAIL_ALREADY_VERIFIED);
        }

        if (user.getEmailVerificationExpiry() != null &&
                user.getEmailVerificationExpiry()
                        .before(Timestamp.from(Instant.now()))){

            throw new VerificationTokenExpiredException(
                    EmailConstants.VERIFICATION_TOKEN_EXPIRED);
        }

        user.verifyEmail();
        userRepository.save(user);
        notificationService.markEmailVerificationNotificationAsRead(user);
    }
}
