package com.example.backend.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.backend.dto.LoginRequest;
import com.example.backend.dto.SignupRequest;
import com.example.backend.dto.TokenResponse;
import com.example.backend.services.AuthService;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private static final Logger logger =
            LoggerFactory.getLogger(AuthController.class);

    @Autowired
    private AuthService authService;

    // ===================== SIGNUP =====================
    @PostMapping("/signup")
    public ResponseEntity<TokenResponse> signup(
            @RequestBody SignupRequest request) {

        try {
            logger.info("Signup request received | username={}", request.getUsername());

            TokenResponse response = authService.signup(request);

            if (!response.isSuccess()) {
                logger.warn("Signup failed | {}", response.getMessage());
                return ResponseEntity
                        .status(HttpStatus.BAD_REQUEST)
                        .body(response);
            }

            logger.info("Signup successful | {}", response.getMessage());
            return ResponseEntity
                    .status(HttpStatus.CREATED)
                    .body(response);

        } catch (Exception e) {
            logger.error("Signup error", e);
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new TokenResponse(false, null, "Internal server error"));
        }
    }

    // ===================== LOGIN =====================
    @PostMapping("/login")
    public ResponseEntity<TokenResponse> login(
            @RequestBody LoginRequest request) {

        try {
            logger.info("Login request received | username={}", request.getUsername());

            TokenResponse response = authService.login(request);

            if (!response.isSuccess()) {
                logger.warn("Login failed | {}", response.getMessage());
                return ResponseEntity
                        .status(HttpStatus.UNAUTHORIZED)
                        .body(response);
            }

            logger.info("Login successful | {}", response.getMessage());
            return ResponseEntity.ok(response);

        } catch (Exception e) {
            logger.error("Login error", e);
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new TokenResponse(false, null, "Internal server error"));
        }
    }
}
