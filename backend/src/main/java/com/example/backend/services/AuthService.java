package com.example.backend.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.backend.dto.LoginRequest;
import com.example.backend.dto.SignupRequest;
import com.example.backend.dto.TokenResponse;
import com.example.backend.models.User;
import com.example.backend.models.UserLogin;
import com.example.backend.repository.UserLoginRepository;
import com.example.backend.repository.UserRepository;
import com.example.backend.utility.JwtUtil;

import java.util.Map;
import java.util.Optional;

@Service
public class AuthService {

    private static final Logger logger =
            LoggerFactory.getLogger(AuthService.class);

    @Autowired
    private UserLoginRepository userLoginRepo;

    @Autowired
    private UserRepository userRepo;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtUtil jwtUtil;

    // ===================== SIGNUP =====================
    public TokenResponse signup(SignupRequest request) {
        try {
            Optional<UserLogin> existingUser =
                    userLoginRepo.findByUsername(request.getUsername());

            if (existingUser.isPresent()) {
                logger.warn("Signup failed | Username already exists");
                return new TokenResponse(false, "Username already exists", null);
            }

            UserLogin userLogin = new UserLogin();
            userLogin.setUsername(request.getUsername());
            userLogin.setPassword(
                    passwordEncoder.encode(request.getPassword()));
            userLoginRepo.save(userLogin);

            User user = new User();
            user.setId(userLogin.getId());
            user.setName(userLogin.getUsername());
            user.setAmount(1000);
            userRepo.save(user);

            logger.info("User registered successfully | username={}",
                    request.getUsername());

            return new TokenResponse(
                    true,
                    "User registered successfully",
                    null
            );

        } catch (Exception e) {
            logger.error("Signup error", e);
            return new TokenResponse(
                    false,
                    "Signup failed due to server error",
                    null
            );
        }
    }

    // ===================== LOGIN =====================
    public TokenResponse login(LoginRequest request) {
        try {
            Optional<UserLogin> optionalUser =
                    userLoginRepo.findByUsername(request.getUsername());

            if (optionalUser.isEmpty()) {
                logger.warn("Login failed | Invalid username");
                return new TokenResponse(
                        false,
                        "Invalid username or password",
                        null
                );
            }

            UserLogin user = optionalUser.get();

            if (!passwordEncoder.matches(
                    request.getPassword(), user.getPassword())) {
                logger.warn("Login failed | Password mismatch");
                return new TokenResponse(
                        false,
                        "Invalid username or password",
                        null
                );
            }

            String token = jwtUtil.generateToken(user.getUsername());

            logger.info("Login successful | username={}",
                    user.getUsername());

            return new TokenResponse(
                    true,
                    "Login successful",
                    Map.of(
                            "token", token,
                            "userId", user.getId()
                    )
            );

        } catch (Exception e) {
            logger.error("Login error", e);
            return new TokenResponse(
                    false,
                    "Login failed due to server error",
                    null
            );
        }
    }
}
