package com.example.usermanagement.controller;

import com.example.usermanagement.model.Project;
import com.example.usermanagement.model.Role;
import com.example.usermanagement.model.User;
import com.example.usermanagement.payload.CreateProjectRequest;
import com.example.usermanagement.payload.UpdateProjectRequest;
import com.example.usermanagement.repository.ProjectRepository;
import com.example.usermanagement.repository.RoleRepository;
import com.example.usermanagement.repository.UserRepository;
import com.example.usermanagement.security.CurrentUser;
import com.example.usermanagement.security.UserPrincipal;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/projects")
@AllArgsConstructor
public class ProjectController {

    private final UserRepository userRepository;

    private final RoleRepository roleRepository;

    private final ProjectRepository projectRepository;

    @GetMapping
    public ResponseEntity<List<Project>> getProjects(@CurrentUser UserPrincipal currentUser) {
        Optional<User> user = userRepository.findByFullName(currentUser.getUsername());
        Optional<Role> role = roleRepository.findByName("ROLE_ADMIN");
        System.out.println(user.get().getRoles().contains(role.get()));
        List<Project> projects;
        if(user.get().getRoles().contains(role.get())){
            projects = projectRepository.findAll();
        } else {
            projects = projectRepository.findAllByUser(user.get());
        }
        return ResponseEntity.ok(projects);
    }

    @PostMapping
    public ResponseEntity<Project> createProject(@CurrentUser UserPrincipal currentUser,
                                                 @Valid @RequestBody CreateProjectRequest request) {
        Optional<User> user = userRepository.findByFullName(currentUser.getUsername());
        Project newProject = Project.builder()
                .user(user.get())
                .name(request.getProjectName())
                .build();
        Project project = projectRepository.save(newProject);
        return ResponseEntity.ok(project);
    }

    @PostMapping("/delete/{id}")
    public ResponseEntity<Void> deleteProject(@CurrentUser UserPrincipal currentUser,@PathVariable Long id) {
        Optional<User> user = userRepository.findByFullName(currentUser.getUsername());
        Optional<Role> role = roleRepository.findByName("ROLE_ADMIN");
        Optional<Project> project = projectRepository.findById(id);
        if(user.get().getRoles().contains(role.get())){
            if(project.isPresent() && project.get().getId().equals(id)){
                projectRepository.delete(project.get());
                return ResponseEntity.ok().build();
            }
        } else {
            if(project.isPresent() && project.get().getUser().getId().equals(user.get().getId())){
                projectRepository.delete(project.get());
                return ResponseEntity.ok().build();
            }
        }
        return ResponseEntity.badRequest().build();
    }

    @PostMapping("/update")
    public ResponseEntity<Void> updateProject(@CurrentUser UserPrincipal currentUser,
                                              @Valid @RequestBody UpdateProjectRequest request) {
        Optional<User> user = userRepository.findByFullName(currentUser.getUsername());
        Optional<Role> role = roleRepository.findByName("ROLE_ADMIN");
        Optional<Project> project = projectRepository.findById(request.getId());
        if(user.get().getRoles().contains(role.get())){
            if(project.isPresent() && project.get().getId().equals(request.getId())){
                Project repoProject = project.get();
                repoProject.setName(request.getProjectName());
                projectRepository.save(repoProject);
                return ResponseEntity.ok().build();
            }
        } else {
            if(project.isPresent() &&
                    project.get().getId().equals(request.getId()) &&
                    project.get().getUser().getId().equals(user.get().getId())){
                Project repoProject = project.get();
                repoProject.setName(request.getProjectName());
                projectRepository.save(repoProject);
                return ResponseEntity.ok().build();
            }
        }
        return ResponseEntity.badRequest().build();
    }
}
