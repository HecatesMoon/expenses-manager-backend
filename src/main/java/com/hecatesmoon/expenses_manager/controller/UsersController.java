package com.hecatesmoon.expenses_manager.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

import com.hecatesmoon.expenses_manager.service.UsersService;

@RestController
public class UsersController {
    
    @Autowired
    private final UsersService service;

    public UsersController (UsersService service){
        this.service = service;
    }
}
