package com.example.usermanagement.payload;

import lombok.Data;
import org.hibernate.annotations.NaturalId;

import javax.validation.constraints.*;
@Data
public class SignUpRequest {

    @NotBlank
    @Size(max=40)
    private String fullName;

    @NotBlank
    @Size(max=40)
    private String fullBusinessTitle;

    @NotBlank
    @Size(max=40)
    private String address;

    @NaturalId
    @NotBlank
    @Size(max=50)
    @Email
    private String email;

    @NotBlank
    @Size(max=100)
    private String password;

    private String phone;

    private String workingAddress;

}

