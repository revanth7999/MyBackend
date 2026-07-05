package com.backend.MyBackend.cart.exception;

public class ActiveCartNotFoundException extends RuntimeException{
    public ActiveCartNotFoundException(String message){
        super(message);
    }
}
