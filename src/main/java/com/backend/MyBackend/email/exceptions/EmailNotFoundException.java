package com.backend.MyBackend.email.exceptions;

public class EmailNotFoundException extends RuntimeException{
    public EmailNotFoundException(String message){
        super(message);
    }
}
