package com.example.usermanagement.controller;

import com.example.usermanagement.model.Role;
import com.example.usermanagement.payload.AddRoleRequest;
import com.example.usermanagement.payload.JwtAuthenticationResponse;
import com.example.usermanagement.payload.LoginRequest;
import com.example.usermanagement.repository.RoleRepository;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/roles")
@AllArgsConstructor
public class RolesController {

    private final RoleRepository roleRepository;

    @PostMapping
    public ResponseEntity<Void> authenticateUser(@Valid @RequestBody AddRoleRequest request) {

        Role newRole = Role.builder()
                .name("ROLE_"+request.getRoleName().toUpperCase())
                .build();

        roleRepository.save(newRole);

        return ResponseEntity.ok().build();
    }
}
