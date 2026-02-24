package com.hecatesmoon.expenses_manager.exception;

public class BusinessException extends RuntimeException {
    public BusinessException (String message){
        super(message);
    }
}
