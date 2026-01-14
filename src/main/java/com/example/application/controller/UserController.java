package com.example.application.controller;

import com.example.application.dto.LoginRequest;
import com.example.application.dto.UserRequest;
import com.example.application.entity.User;
import com.example.application.repository.UserRepository;

import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserRepository userRepository;

    public UserController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    // ✅ User Registration
    @PostMapping("/register")
    public String register(@Valid @RequestBody UserRequest request) {

        if (userRepository.findByEmail(request.getEmail()).isPresent()) {
            return "Email already registered";
        }

        User user = new User(
                request.getName(),
                request.getEmail(),
                request.getPassword()
        );

        userRepository.save(user);
        return "User registered successfully";
    }

    // ✅ User Login
    @PostMapping("/login")
    public String login(@Valid @RequestBody LoginRequest request) {

        return userRepository.findByEmail(request.getEmail())
                .filter(u -> u.getPassword().equals(request.getPassword()))
                .map(u -> "Login successful")
                .orElse("Invalid email or password");
    }

    // ✅ View All Users
    @GetMapping
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }
}
