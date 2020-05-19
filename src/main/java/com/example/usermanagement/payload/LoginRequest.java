package com.example.usermanagement.payload;

import lombok.Data;

import javax.validation.constraints.NotBlank;
@Data
public class LoginRequest {
    @NotBlank
    private String fullName;

    @NotBlank
    private String password;
}
