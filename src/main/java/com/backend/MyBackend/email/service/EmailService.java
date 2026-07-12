package com.backend.MyBackend.email.service;

import com.backend.MyBackend.account.entity.User;
import com.backend.MyBackend.account.repository.UserRepository;
import com.backend.MyBackend.email.events.VerificationEmailEvent;
import com.backend.MyBackend.email.exceptions.EmailNotFoundException;
import com.backend.MyBackend.exception.UserNotFoundException;
import java.sql.Timestamp;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
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
    private final EmailProvider emailProvider;

    @Autowired
    private ApplicationEventPublisher applicationEventPublisher;

    public EmailService(UserRepository userRepository,EmailProvider emailProvider){
        this.userRepository = userRepository;
        this.emailProvider = emailProvider;
    }

    public void sendVerificationEmail(Long id){
        System.out.println("UserId:: " + id);
        User user = userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException("User not found"));

        if (user.getEmail() == null || user.getEmail().isBlank()){
            throw new EmailNotFoundException("User email is empty");
        }
        System.out.println(user.getEmail());
        String token = UUID.randomUUID().toString();

        user.setEmailVerificationToken(token);
        user.setEmailVerificationExpiry(
                new Timestamp(
                        System.currentTimeMillis() + 24 * 60 * 60 * 1000));

        userRepository.save(user);
        applicationEventPublisher.publishEvent(
                new VerificationEmailEvent(
                        user.getEmail(),
                        token));
    }

    public void verifyEmail(String token){
        User user = userRepository.findByEmailVerificationToken(token);

        if (user == null){
            throw new RuntimeException("Invalid verification token");
        }

        if (user.getIsEmailVerified()){
            throw new RuntimeException("Email already verified");
        }

        if (user.getEmailVerificationExpiry() != null &&
                user.getEmailVerificationExpiry()
                        .before(new Timestamp(System.currentTimeMillis()))){

            throw new RuntimeException("Verification link expired");
        }

        user.setEmailVerified(true);
        user.setEmailVerificationToken(null);
        user.setEmailVerificationExpiry(null);

        userRepository.save(user);
    }
}
