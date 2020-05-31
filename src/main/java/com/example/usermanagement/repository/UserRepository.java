package com.example.usermanagement.repository;

import com.example.usermanagement.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.example.usermanagement.model.User;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User,Long>{

    Optional<User> findByFullName(String fullName);
    boolean existsByFullName(String fullName);
    boolean existsByEmail(String email);
}
