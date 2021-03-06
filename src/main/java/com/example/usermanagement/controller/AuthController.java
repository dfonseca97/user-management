package com.example.usermanagement.controller;


import com.example.usermanagement.exception.AppException;
import com.example.usermanagement.model.Role;
import com.example.usermanagement.model.User;

import com.example.usermanagement.payload.ApiResponse;
import com.example.usermanagement.payload.ChangePasswordRequest;
import com.example.usermanagement.payload.JwtAuthenticationResponse;
import com.example.usermanagement.payload.LoginRequest;
import com.example.usermanagement.payload.SignUpRequest;
import com.example.usermanagement.repository.RoleRepository;
import com.example.usermanagement.repository.UserRepository;
import com.example.usermanagement.security.CustomUserDetailsService;
import com.example.usermanagement.security.JwtTokenProvider;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.Collections;
import java.util.Optional;

@RestController
@RequestMapping("/api/auth")
@AllArgsConstructor
public class AuthController {

    private final AuthenticationManager authenticationManager;

    private final UserRepository userRepository;

    private final RoleRepository roleRepository;

    private final PasswordEncoder passwordEncoder;

    private final JwtTokenProvider tokenProvider;

    private final CustomUserDetailsService customUserDetailsService;

    @PostMapping("/signin")
    public ResponseEntity<JwtAuthenticationResponse> authenticateUser(@Valid @RequestBody LoginRequest request) {
        Optional<User> user = userRepository.findByFullName(request.getFullName());
        if(user.isPresent()){
            if(!user.get().getActive()){
                return new ResponseEntity(new ApiResponse(false, "Bad Credentials"),
                        HttpStatus.BAD_REQUEST);
            }
            if(passwordEncoder.matches(request.getPassword(), user.get().getPassword())) {
                Authentication authentication = authenticationManager.authenticate(
                        new UsernamePasswordAuthenticationToken(
                                request.getFullName(),
                                request.getPassword()
                        )
                );

                SecurityContextHolder.getContext().setAuthentication(authentication);

                String jwt = tokenProvider.generateToken(authentication);
                return ResponseEntity.ok(new JwtAuthenticationResponse(jwt));
            }
            else {
                return new ResponseEntity(new ApiResponse(false, "Bad Credentials"),
                        HttpStatus.BAD_REQUEST);
            }

        }
        else {
            return new ResponseEntity(new ApiResponse(false, "Bad Credentials"),
                    HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/signup")
    public ResponseEntity<JwtAuthenticationResponse> registerUser(@Valid @RequestBody SignUpRequest request) {
        if(userRepository.existsByFullName(request.getFullName())) {
            return new ResponseEntity(new ApiResponse(false, "Username is already taken!"),
                    HttpStatus.BAD_REQUEST);
        }

        if(userRepository.existsByEmail(request.getEmail())) {
            return new ResponseEntity(new ApiResponse(false, "Email Address already in use!"),
                    HttpStatus.BAD_REQUEST);
        }
        User user = User.builder()
                .address(request.getAddress())
                .email(request.getEmail())
                .fullBusinessTitle(request.getFullBusinessTitle())
                .fullName(request.getFullName())
                .password(passwordEncoder.encode(request.getPassword()))
                .phone(request.getPhone())
                .workingAddress(request.getWorkingAddress())
                .active(Boolean.TRUE)
                .build();


        Role userRole = roleRepository.findByName("ROLE_USER")
                .orElseThrow(() -> new AppException("User Role not set."));

        user.setRoles(Collections.singleton(userRole));

        User savedUser = userRepository.save(user);

        String jwt = tokenProvider.generateTokenByUser(savedUser);


        return ResponseEntity.ok(new JwtAuthenticationResponse(jwt));
    }

    @PostMapping("/forgotPassword")
    public ResponseEntity<Void> forgotPassword(@Valid @RequestBody ChangePasswordRequest request){
        Optional<User> user = userRepository.findByFullName(request.getUsername());
        if(user.isPresent()){
            User repoUser = user.get();
            repoUser.setPassword(passwordEncoder.encode(request.getNewPassword()));
            userRepository.save(repoUser);
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.badRequest().build();
    }
}
