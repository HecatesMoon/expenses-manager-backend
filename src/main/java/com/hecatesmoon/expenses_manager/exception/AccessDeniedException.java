package com.hecatesmoon.expenses_manager.exception;

public class AccessDeniedException extends RuntimeException{
    public AccessDeniedException (String message){
        super(message);
    }    
}
