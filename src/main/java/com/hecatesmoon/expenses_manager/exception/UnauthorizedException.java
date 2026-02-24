package com.hecatesmoon.expenses_manager.exception;

public class UnauthorizedException extends RuntimeException {
    public UnauthorizedException (String message){
        super(message);
    }
}
