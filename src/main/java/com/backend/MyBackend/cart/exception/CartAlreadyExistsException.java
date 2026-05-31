package com.backend.MyBackend.cart.exception;

public class CartAlreadyExistsException extends RuntimeException{
    public CartAlreadyExistsException(String message){
        super(message);
    }
}
