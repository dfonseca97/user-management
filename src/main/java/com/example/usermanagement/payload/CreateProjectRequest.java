package com.example.usermanagement.payload;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class CreateProjectRequest {
    @NotBlank
    private String projectName;

}
