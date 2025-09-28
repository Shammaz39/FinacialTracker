package com.example.fintrack.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Getter
@Setter
public class RegisterRequest {
    @Schema(example = "Shammaz", description = "Name")
    private String name;
    @Schema(example = "ibrahimshammaz39@gmail.com", description = "Email")
    private String email;
    @Schema(example = "qqq11", description = "Password")
    private String password;
}
