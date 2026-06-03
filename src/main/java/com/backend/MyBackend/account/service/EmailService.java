package com.backend.MyBackend.account.service;

import com.backend.MyBackend.account.entity.User;
import com.backend.MyBackend.account.repository.UserRepository;
import java.sql.Timestamp;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
public class EmailService{

    @Value("${app.frontendUrl}")
    private String frontendUrl;

    private final UserRepository userRepository;
    private final JavaMailSender javaMailSender;

    public EmailService(UserRepository userRepository,JavaMailSender javaMailSender){
        this.userRepository = userRepository;
        this.javaMailSender = javaMailSender;
    }

    public void sendVerificationEmail(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        String username = authentication.getName();
        System.out.println("Username:: " + username);
        User user = userRepository.findByUsername(username);
        if (user == null){
            throw new RuntimeException("User not found");
        }
        System.out.println(user.getEmail());
        String token = UUID.randomUUID().toString();

        user.setEmailVerificationToken(token);
        user.setEmailVerificationExpiry(
                new Timestamp(
                        System.currentTimeMillis() + 24 * 60 * 60 * 1000));

        userRepository.save(user);

        String verificationUrl = frontendUrl + "/verify-email?token="
                + token;

        SimpleMailMessage message = new SimpleMailMessage();

        message.setTo(user.getEmail());
        message.setSubject("Verify Your Email");
        message.setText(
                "Click the link below to verify your email:\n\n"
                        + verificationUrl);

        javaMailSender.send(message);
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
