package com.hecatesmoon.expenses_manager.dto;

import com.hecatesmoon.expenses_manager.model.User;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public class RegisterRequest {
    
    @NotBlank(message = "Write something")
    @Size(min = 2, max = 50, message = "The first name has to have between 2 and 50 characters")
    private String firstName;

    @NotBlank(message = "Write something")
    @Size(min = 2, max = 50, message = "The last name has to have between 2 and 50 characters")
    private String lastName;

    @Email(message = "Write a valid email")
    @Size(min = 5, max = 100, message = "The email has to have between 5 and 100 characters")
    private String email;

    @Size(min = 8, max = 100, message = "The password has to have between 8 and 100 characters")
    @Pattern(regexp = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=\\S+$).{8,}$", message = "The password needs to at leas have: a number, a uppercase and a lowcase letter, without spaces")
    private String password;

    private String confirmPassword;

    public static User toEntity(RegisterRequest request) {
        User user = new User();
        user.setFirstName(request.firstName);
        user.setLastName(request.lastName);
        user.setEmail(request.email);
        user.setPassword(request.password);
        return user;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public String getConfirmPassword() {
        return confirmPassword;
    }
    
}
