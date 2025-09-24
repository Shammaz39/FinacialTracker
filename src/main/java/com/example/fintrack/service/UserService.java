package com.example.fintrack.service;

import com.example.fintrack.dto.AuthResponse;
import com.example.fintrack.dto.LoginRequest;
import com.example.fintrack.dto.RegisterRequest;
import com.example.fintrack.entity.User;
import com.example.fintrack.repository.UserRepository;
import com.example.fintrack.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.http.HttpStatus;

import java.util.Optional;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;  // we’ll define in SecurityConfig

    @Autowired
    private JwtUtil jwtUtil;

    // ✅ Register a new user
    public AuthResponse register(RegisterRequest request) {
        if (userRepository.existsByEmail(request.getEmail())) {
            return new AuthResponse(null, "Email already exists!",HttpStatus.CONFLICT);
        }

        User user = new User();
        user.setName(request.getName());
        user.setEmail(request.getEmail());
        user.setPassword(passwordEncoder.encode(request.getPassword()));

        userRepository.save(user);

        // generate JWT
        String token = jwtUtil.generateToken(user.getEmail());

        return new AuthResponse(token, "User registered successfully",HttpStatus.CREATED);
    }

    // ✅ Login user
    public AuthResponse login(LoginRequest request) {
        Optional<User> optionalUser = userRepository.findByEmail(request.getEmail());

        if (optionalUser.isEmpty()) {
            return new AuthResponse(null, "Email not found", HttpStatus.UNAUTHORIZED);
        }

        if (!passwordEncoder.matches(request.getPassword(), optionalUser.get().getPassword())) {
            return new AuthResponse(null, "Invalid email or password", HttpStatus.UNAUTHORIZED);
        }

        String token = jwtUtil.generateToken(optionalUser.get().getEmail());
        return new AuthResponse(token, "Login successful", HttpStatus.ACCEPTED);
    }
}
