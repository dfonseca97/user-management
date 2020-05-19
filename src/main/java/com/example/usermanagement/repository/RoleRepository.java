package com.example.usermanagement.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.example.usermanagement.model.Role;
import com.example.usermanagement.model.RoleName;

import java.util.Optional;

public interface RoleRepository extends JpaRepository<Role,Long> {
    Optional<Role> findByName(RoleName roleName);
}
