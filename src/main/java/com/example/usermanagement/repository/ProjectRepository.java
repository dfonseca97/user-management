package com.example.usermanagement.repository;

import com.example.usermanagement.model.Project;
import com.example.usermanagement.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProjectRepository extends JpaRepository<Project,Long> {
    List<Project> findAllByUser(User user);
}
