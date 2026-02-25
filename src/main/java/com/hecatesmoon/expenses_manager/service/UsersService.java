package com.hecatesmoon.expenses_manager.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.hecatesmoon.expenses_manager.dto.LoginRequest;
import com.hecatesmoon.expenses_manager.exception.BusinessException;
import com.hecatesmoon.expenses_manager.model.User;
import com.hecatesmoon.expenses_manager.repository.UsersRepository;

@Service
public class UsersService {
    @Autowired
    private final UsersRepository repository;
    @Autowired
    private final PasswordEncoder passwordEncoder;

    public UsersService(UsersRepository repository, PasswordEncoder passwordEncoder){
        this.repository = repository;
        this.passwordEncoder = passwordEncoder;
    }

    //todo: manage null or use exception
    public User getUserById (Long id){
        return this.repository.findById(id).orElse(null);
    }

    public User createUser(User user){

        newUserValidation(user);

        String newPassword = passwordEncoder.encode(user.getPassword());
        user.setPassword(newPassword);

        return this.repository.save(user);
    }

    public User loginValidation(LoginRequest login){
        User user = repository.findByEmail(login.getEmail())
                              .orElseThrow(() -> new BusinessException("Email or Password not valid"));
        
        if (!passwordEncoder.matches(login.getPassword(), user.getPassword())){
            throw new BusinessException("Email or Password not valid");
        }

        return user;
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