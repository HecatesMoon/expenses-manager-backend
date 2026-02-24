package com.hecatesmoon.expenses_manager.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.hecatesmoon.expenses_manager.model.User;

@Repository
public interface UsersRepository extends JpaRepository<User, Long> {
    User save(User user);

    Boolean existsByEmail(String email);
}