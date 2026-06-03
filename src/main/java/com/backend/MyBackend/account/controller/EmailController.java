package com.backend.MyBackend.account.controller;

import com.backend.MyBackend.account.service.EmailService;
import com.backend.MyBackend.common.dto.ApiResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/email")
public class EmailController{

    private final EmailService emailService;

    public EmailController(EmailService emailService){
        this.emailService = emailService;
    }

    @PostMapping("/send-verification-email")
    public ResponseEntity<ApiResponse> sendVerificationEmail(){
        emailService.sendVerificationEmail();
        return ResponseEntity.ok(new ApiResponse("Verification email sent",""));
    }

    @GetMapping("/verify-email")
    public ResponseEntity<ApiResponse> verifyEmail(
            @RequestParam String token){
        emailService.verifyEmail(token);

        return ResponseEntity.ok(new ApiResponse("Email verified successfully",""));
    }
}
