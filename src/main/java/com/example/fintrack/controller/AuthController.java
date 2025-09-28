package com.example.fintrack.controller;

import com.example.fintrack.dto.AuthResponse;
import com.example.fintrack.dto.LoginRequest;
import com.example.fintrack.dto.RegisterRequest;
import com.example.fintrack.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Tag(name = "Authentication", description = "APIs for user registration and login")
public class AuthController {
    @Autowired
    private UserService userService;

    @GetMapping("/test")
    @Operation(summary = "Test API", description = "Checks if the service is up")
    public ResponseEntity<?> check() {
        return ResponseEntity.ok("testing is done");
    }

    @PostMapping("/register")
    @Operation(summary = "Register user", description = "Registers a new user",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "User registered successfully",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = AuthResponse.class)
                            )
                    ),
                    @ApiResponse(
                            responseCode = "409",
                            description = "Email already exists!"
                    )
            })
    public ResponseEntity<?> register(@RequestBody RegisterRequest request) {
        AuthResponse response = userService.register(request);
        return ResponseEntity
                .status(response.getStatus())
                .body(response);
    }

    @PostMapping("/login")
    @Operation(summary = "Login user", description = "Logs in a user and returns token",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Login successful",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = AuthResponse.class)
                            )
                    ),
                    @ApiResponse(
                            responseCode = "401",
                            description = "Email not found"
                    ),
                    @ApiResponse(
                            responseCode = "401",
                            description = "Invalid email or password"
                    )
            })
    public ResponseEntity<?> login(@RequestBody LoginRequest request) {
        AuthResponse response = userService.login(request);
        return ResponseEntity
                .status(response.getStatus())
                .body(response);
    }
}
