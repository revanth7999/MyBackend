package com.backend.MyBackend.email.exceptions;

public class VerificationTokenExpiredException extends RuntimeException{
    public VerificationTokenExpiredException(String message){
        super(message);
    }
}
