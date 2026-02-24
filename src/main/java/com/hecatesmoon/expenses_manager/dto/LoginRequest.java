package com.hecatesmoon.expenses_manager.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public class LoginRequest {
    @Email(message = "write a valid email")
    @NotBlank(message = "write an email")
    private String email;
    @NotBlank(message = "write a password")
    private String password;

    public LoginRequest () {}

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    
}
