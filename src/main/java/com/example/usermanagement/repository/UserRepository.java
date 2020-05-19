package com.example.usermanagement.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.example.usermanagement.model.User;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User,Long>{
    long countByBaskets(Long userId);

    Optional<User> findByUsername(String username);
    boolean existsByUsername(String username);
    boolean existsByEmail(String email);
}
