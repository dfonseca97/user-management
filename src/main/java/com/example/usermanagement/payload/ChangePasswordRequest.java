package com.example.usermanagement.payload;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class ChangePasswordRequest {
    @NotBlank
    private String username;

    @NotBlank
    private String newPassword;
}
