package com.hexaware.hexacliq.controller;

import com.hexaware.hexacliq.dao.IProjectRepository;
import com.hexaware.hexacliq.dto.Project;
import com.hexaware.hexacliq.dto.User;
import com.hexaware.hexacliq.exception.DataNotFoundException;
import com.hexaware.hexacliq.service.ProjectService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/projects")
@CrossOrigin("*")
@Slf4j
public class ProjectController {
    @Autowired
    ProjectService projectService;

    @Autowired
    IProjectRepository projectRepository;

    @PostMapping
    public ResponseEntity<Object> createProject(@RequestBody Project project, Authentication authentication) {
        User user = (User) authentication.getPrincipal();
        project.setCreatedBy(user.getUsername());
        project.setCreatedTime(LocalDate.now());
        project.setModifiedBy(user.getUsername());
        project.setModifiedTime(LocalDate.now());
        return ResponseEntity.ok(projectService.save(project));
    }

    @PutMapping
    public ResponseEntity<Object> updateProject(@RequestBody Project project, Authentication authentication) {
        User user = (User) authentication.getPrincipal();
        project.setModifiedBy(user.getUsername());
        project.setModifiedTime(LocalDate.now());
        return ResponseEntity.ok(projectService.save(project));
    }

    @GetMapping("/{projectId}")
    public ResponseEntity<Object> findProject(@PathVariable Integer projectId) {
        Project project = projectService.findById(projectId)
                .orElseThrow(() -> new DataNotFoundException("Project not found"));
        return new ResponseEntity<>(project, HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<Object> findProjects() {
        List<Project> projects = projectRepository.findAll();
        return new ResponseEntity<>(projects, HttpStatus.OK);
    }

}
