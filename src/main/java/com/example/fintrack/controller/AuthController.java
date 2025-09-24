package com.example.fintrack.controller;

import com.example.fintrack.dto.AuthResponse;
import com.example.fintrack.dto.LoginRequest;
import com.example.fintrack.dto.RegisterRequest;
import com.example.fintrack.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AuthController {
    @Autowired
    private UserService userService;

    @GetMapping("/test")
    public ResponseEntity<?> check() {
        return ResponseEntity.ok("testing is done");
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody RegisterRequest request) {
        AuthResponse response = userService.register(request);
        return ResponseEntity
                .status(response.getStatus())
                .body(response);
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest request) {
        AuthResponse response = userService.login(request);
        return ResponseEntity
                .status(response.getStatus())
                .body(response);
    }
}
