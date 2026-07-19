package com.backend.MyBackend.exception;

public class InvalidExistingPasswordException extends RuntimeException{
    public InvalidExistingPasswordException(String message){
        super(message);
    }
}
