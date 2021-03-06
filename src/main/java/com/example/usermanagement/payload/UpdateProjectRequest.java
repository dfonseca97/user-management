package com.example.usermanagement.payload;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class UpdateProjectRequest {
    private Long id;

    @NotBlank
    private String projectName;
}
