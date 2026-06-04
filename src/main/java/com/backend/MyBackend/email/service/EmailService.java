package com.backend.MyBackend.email.service;

import com.backend.MyBackend.account.entity.User;
import com.backend.MyBackend.account.repository.UserRepository;
import java.sql.Timestamp;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
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

    public EmailService(UserRepository userRepository,EmailProvider emailProvider){
        this.userRepository = userRepository;
        this.emailProvider = emailProvider;
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

        String to = user.getEmail();
        String subject = "Verify your email";
        String verificationUrl = frontendUrl + "/#/verify-email?token="
                + token;
        String html = """
                    <p>Click the link below to verify your email:</p>
                    <a href="%s">Verify Email</a>
                """.formatted(verificationUrl);

        try{
            emailProvider.sendEmail(to,subject,html);
            userRepository.save(user);
        } catch (Exception e){
            throw new RuntimeException("Email sending failed");
        }
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
