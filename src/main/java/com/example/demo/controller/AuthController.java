package com.example.demo.controller;

import com.example.demo.model.User;
import com.example.demo.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/auth")
public class AuthController {
    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
    // In-memory session store for demo
    private final Map<String, Long> sessions = new HashMap<>();

    @Autowired
    public AuthController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @PostMapping("/register")
    public String register(@RequestBody User user) {
        if (userRepository.findByUsername(user.getUsername()).isPresent()) {
            return "Username already exists";
        }
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepository.save(user);
        return "Registered successfully";
    }

    @PostMapping("/login")
    public Map<String, String> login(@RequestBody Map<String, String> req) {
        Optional<User> userOpt = userRepository.findByUsername(req.get("username"));
        if (userOpt.isPresent() && passwordEncoder.matches(req.get("password"), userOpt.get().getPassword())) {
            String token = UUID.randomUUID().toString();
            sessions.put(token, userOpt.get().getId());
            return Map.of("token", token);
        }
        return Map.of("error", "Invalid credentials");
    }

    @GetMapping("/profile")
    public User getCurrentUserProfile(@RequestHeader("X-Auth-Token") String token) {
        Long userId = sessions.get(token);
        return userId == null ? null : userRepository.findById(userId).orElse(null);
    }

    public Long getUserIdFromToken(String token) {
        return sessions.get(token);
    }
} 