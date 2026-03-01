package com.hecatesmoon.expenses_manager.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import com.hecatesmoon.expenses_manager.dto.LoginRequest;
import com.hecatesmoon.expenses_manager.dto.RegisterRequest;
import com.hecatesmoon.expenses_manager.dto.UserResponse;
import com.hecatesmoon.expenses_manager.service.UsersService;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.GetMapping;

@RestController
public class UsersController {
    
    @Autowired
    private final UsersService service;

    public UsersController (UsersService service){
        this.service = service;
    }

    @PostMapping("/api/auth/register")
    public ResponseEntity<UserResponse> register(@Valid @RequestBody RegisterRequest user, HttpSession session) {
        UserResponse newUser = this.service.createUser(user);

        session.setAttribute("user_id", newUser.getId());
        return ResponseEntity.ok(newUser);
    }

    @PostMapping("/api/auth/login")
    public ResponseEntity<UserResponse> login(@Valid @RequestBody LoginRequest login, HttpSession session) {
        UserResponse user = service.loginValidation(login);

        session.setAttribute("user_id", user.getId());

        return ResponseEntity.ok(user);
    }
    
    @GetMapping("/api/auth/logout")
    public ResponseEntity<Map<String, String>> logout(HttpSession session) { 
        session.invalidate();
        return ResponseEntity.ok(Map.of("message","Logged out succesfully"));
    }
     
}