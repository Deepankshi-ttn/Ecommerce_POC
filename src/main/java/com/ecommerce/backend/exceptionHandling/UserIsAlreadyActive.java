package com.ecommerce.backend.exceptionHandling;

public class UserIsAlreadyActive extends RuntimeException {
    public UserIsAlreadyActive(String message){
        super(message);
    }
}