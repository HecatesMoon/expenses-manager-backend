package com.hecatesmoon.expenses_manager.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hecatesmoon.expenses_manager.exception.BusinessException;
import com.hecatesmoon.expenses_manager.model.User;
import com.hecatesmoon.expenses_manager.repository.UsersRepository;

@Service
public class UsersService {
    @Autowired
    private final UsersRepository repository;

    public UsersService(UsersRepository repository){
        this.repository = repository;
    }

    public User createUser(User user){

        newUserValidation(user);

        return this.repository.save(user);
    }

    private void newUserValidation(User user){
        
        if (!user.getPassword().equals(user.getConfirmPassword())){
            throw new BusinessException("Passwords are not equal");
        }
        if (repository.existsByEmail(user.getEmail())){
            throw new BusinessException("This email already exists");
        }
    }
}