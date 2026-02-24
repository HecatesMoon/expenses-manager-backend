package com.hecatesmoon.expenses_manager.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import com.hecatesmoon.expenses_manager.dto.LoginRequest;
import com.hecatesmoon.expenses_manager.model.User;
import com.hecatesmoon.expenses_manager.service.UsersService;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@RestController
public class UsersController {
    
    @Autowired
    private final UsersService service;

    public UsersController (UsersService service){
        this.service = service;
    }

    @PostMapping("/api/user/create")
    public ResponseEntity<User> postMethodName(@Valid @RequestBody User user, HttpSession session) {
        User newUser = this.service.createUser(user);

        session.setAttribute("user_id", newUser.getId());
        return ResponseEntity.ok(newUser);
    }

    @PostMapping("/api/user/login")
    public ResponseEntity<User> postMethodName(@Valid @RequestBody LoginRequest login, HttpSession session) {
        User user = service.loginValidation(login);

        session.setAttribute("user_id", user.getId());

        return ResponseEntity.ok(user);
    }
    
    
}