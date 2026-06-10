package com.backend.MyBackend.email.events;

public record VerificationEmailEvent(String email,
        String token) {
}
