package com.backend.MyBackend.exception;

public class UserIdRequiredException extends RuntimeException{
    public UserIdRequiredException(String message){
        super(message);
    }
}
