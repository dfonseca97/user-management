package com.example.usermanagement.controller;

import com.example.usermanagement.model.User;
import com.example.usermanagement.payload.ChangePasswordRequest;
import com.example.usermanagement.payload.DisableUserRequest;
import com.example.usermanagement.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.Optional;

@RestController
@RequestMapping("/api/users")
@AllArgsConstructor
public class UserController {

    private final PasswordEncoder passwordEncoder;

    private final UserRepository userRepository;

    @PostMapping("/changePassword")
    public ResponseEntity<Void> changePassword(@Valid @RequestBody ChangePasswordRequest request){
        Optional<User> user = userRepository.findByFullName(request.getUsername());
        if(user.isPresent()){
            User repoUser = user.get();
            repoUser.setPassword(passwordEncoder.encode(request.getNewPassword()));
            userRepository.save(repoUser);
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.badRequest().build();
    }

    @PostMapping("/disable")
    public ResponseEntity<Void> disableUser(@Valid @RequestBody DisableUserRequest request){
        Optional<User> user = userRepository.findByFullName(request.getUsername());
        if(user.isPresent()){
            User repoUser = user.get();
            repoUser.setActive(Boolean.FALSE);
            userRepository.save(repoUser);
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.badRequest().build();
    }

}
