package com.medvault.backend;

import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    public AuthController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody RegisterRequest req) {
        if (req.fullName() == null || req.email() == null
                || req.password() == null || req.password().length() < 8) {
            return ResponseEntity.badRequest()
                    .body(Map.of("error", "Name, email and a password of at least 8 characters are required"));
        }
        if (userRepository.existsByEmail(req.email())) {
            return ResponseEntity.status(409).body(Map.of("error", "Email already registered"));
        }

        User user = new User();
        user.setFullName(req.fullName());
        user.setEmail(req.email());
        user.setPasswordHash(passwordEncoder.encode(req.password()));
        user.setRole(Role.CITIZEN);
        userRepository.save(user);

        return ResponseEntity.status(201).body(Map.of(
                "id", user.getId(),
                "email", user.getEmail(),
                "role", user.getRole().name()));
    }
}