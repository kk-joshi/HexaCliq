package com.hexaware.hexacliq.service;

import com.hexaware.hexacliq.dao.IProjectRepository;
import com.hexaware.hexacliq.dto.Project;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ProjectService {
    @Autowired
    IProjectRepository repository;

    @Transactional
    public Project save(Project project) {
        return repository.save(project);
    }

    public Optional<Project> findById(Integer id) {
        return repository.findById(id);
    }
}
