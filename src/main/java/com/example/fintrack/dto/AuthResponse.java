package com.example.fintrack.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.http.HttpStatus;

@Data
@AllArgsConstructor
public class AuthResponse {
    private String token;
    private String message;
    private HttpStatus status; // add this field
}
