package com.hecatesmoon.expenses_manager.model;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

@Entity
@Table(name="usuarios")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
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
    @Pattern(regexp = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=\\\\S+$).{8,}$", message = "The password needs to at leas have: a number, a uppercase and a lowcase letter, without spaces")
    private String password;
    @Transient
    private String confirmPassword;
    @Column(name="created_at")
    private LocalDateTime createdAt;
    @Column(name="updated_at")
    private LocalDateTime updatedAt;

    @PrePersist
    protected void onCreate(){
        createdAt = LocalDateTime.now();
        updatedAt = LocalDateTime.now();
    }

    @PreUpdate
    protected void onUpdate(){
        updatedAt = LocalDateTime.now();
    }
}
