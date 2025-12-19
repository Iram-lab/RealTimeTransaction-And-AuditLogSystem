package com.example.backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.backend.models.UserLogin;

import java.util.Optional;

public interface UserLoginRepository extends JpaRepository<UserLogin, Long> {
    Optional<UserLogin> findByUsername(String username);
}
