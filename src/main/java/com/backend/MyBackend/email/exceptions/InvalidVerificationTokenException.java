package com.backend.MyBackend.email.exceptions;

public class InvalidVerificationTokenException extends RuntimeException{
    public InvalidVerificationTokenException(String message){
        super(message);
    }
}
