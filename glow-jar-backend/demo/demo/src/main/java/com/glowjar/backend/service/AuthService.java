package com.glowjar.backend.service;

import com.glowjar.backend.dto.AuthResponse;
import com.glowjar.backend.dto.LoginRequest;
import com.glowjar.backend.dto.RegisterRequest;
import com.glowjar.backend.entities.User;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import com.glowjar.backend.interfaces.UserRepo;

@Service
public class AuthService {
    private final UserRepo UserRepo;
    private final PasswordEncoder passwordEncoder;

    public AuthService(UserRepo UserRepo, PasswordEncoder passwordEncoder) {
        this.UserRepo = UserRepo;
        this.passwordEncoder = passwordEncoder;
    }

    public AuthResponse register(RegisterRequest request) {
        if (UserRepo.existsByEmail(request.getEmail())) {
            throw new RuntimeException("Exista deja un cont cu acest email.");
        }

        User user = new User();
        user.setFirstName(request.getFirstName());
        user.setLastName(request.getLastName());
        user.setEmail(request.getEmail());
        user.setPassword(passwordEncoder.encode(request.getPassword()));

        User savedUser = UserRepo.save(user);

        return new AuthResponse(
                savedUser.getId(),
                savedUser.getFirstName(),
                savedUser.getLastName(),
                savedUser.getEmail()
        );
    }

    public AuthResponse login(LoginRequest request) {
        User user = UserRepo.findByEmail(request.getEmail())
                .orElseThrow(() -> new RuntimeException("Email sau parola incorecta."));

        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new RuntimeException("Email sau parola incorecta.");
        }

        return new AuthResponse(
                user.getId(),
                user.getFirstName(),
                user.getLastName(),
                user.getEmail()
        );
    }
}
