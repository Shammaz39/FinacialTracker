package com.example.fintrack.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.http.HttpStatus;

@Data
@AllArgsConstructor
public class AuthResponse {
    @Schema(description = "JWT Token")
    private String token;
    @Schema(description = "Success Message")
    private String message;
    private HttpStatus status; // add this field
}
