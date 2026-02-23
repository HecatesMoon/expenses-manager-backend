package com.hecatesmoon.expenses_manager.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hecatesmoon.expenses_manager.repository.UsersRepository;

@Service
public class UsersService {
    @Autowired
    private final UsersRepository repository;

    public UsersService(UsersRepository repository){
        this.repository = repository;
    }
}