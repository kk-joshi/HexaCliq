package com.hexaware.hexacliq.controller;

import com.hexaware.hexacliq.dto.Project;
import com.hexaware.hexacliq.exception.DataNotFoundException;
import com.hexaware.hexacliq.service.ProjectService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/projects")
@CrossOrigin("*")
@Slf4j
public class ProjectController {
    @Autowired
    ProjectService projectService;

    @PostMapping("/")
    public ResponseEntity<Object> createProject(@RequestBody Project Project) {
        return ResponseEntity.ok(projectService.save(Project));
    }

    @PutMapping("/")
    public ResponseEntity<Object> updateProject(@RequestBody Project Project) {
        return ResponseEntity.ok(projectService.save(Project));
    }

    @GetMapping("/{projectId}")
    public ResponseEntity<Object> findProject(@PathVariable Integer projectId) {
        Project Project = projectService.findById(projectId)
                .orElseThrow(() -> new DataNotFoundException("Project not found"));
        return ResponseEntity.ok(Project);
    }

}
