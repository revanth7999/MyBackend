package com.backend.MyBackend.exceptions;

public class UserInactiveException extends RuntimeException{
    public UserInactiveException(String message){
        super(message);
    }
}
