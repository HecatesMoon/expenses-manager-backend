package com.hecatesmoon.expenses_manager.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.autoconfigure.WebMvcProperties.Apiversion.Use;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import com.hecatesmoon.expenses_manager.model.User;
import com.hecatesmoon.expenses_manager.service.UsersService;

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
    public ResponseEntity<User> postMethodName(@Valid @RequestBody User user) {
        User newUser = this.service.createUser(user);
        return ResponseEntity.ok(newUser);
    }
    
}