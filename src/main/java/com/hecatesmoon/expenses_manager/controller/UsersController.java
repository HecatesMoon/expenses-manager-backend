package com.hecatesmoon.expenses_manager.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import com.hecatesmoon.expenses_manager.dto.LoginRequest;
import com.hecatesmoon.expenses_manager.dto.RegisterRequest;
import com.hecatesmoon.expenses_manager.dto.UserResponse;
import com.hecatesmoon.expenses_manager.model.User;
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

    @PostMapping("/api/user/create")
    public ResponseEntity<UserResponse> postMethodName(@Valid @RequestBody RegisterRequest user, HttpSession session) {
        UserResponse newUser = this.service.createUser(user);

        session.setAttribute("user_id", newUser.getId());
        return ResponseEntity.ok(newUser);
    }

    @PostMapping("/api/user/login")
    public ResponseEntity<User> postMethodName(@Valid @RequestBody LoginRequest login, HttpSession session) {
        User user = service.loginValidation(login);

        session.setAttribute("user_id", user.getId());

        return ResponseEntity.ok(user);
    }
    
    @GetMapping("/api/user/logout")
    public ResponseEntity<?> getMethodName(HttpSession session) { 
        session.invalidate();
        return ResponseEntity.ok("Logged out succesfully"); //todo: better messaging
    }
     
}