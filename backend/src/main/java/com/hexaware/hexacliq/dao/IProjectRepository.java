package com.hexaware.hexacliq.dao;

import com.hexaware.hexacliq.dto.Project;
import com.hexaware.hexacliq.dto.Project;
import com.hexaware.hexacliq.exception.DataNotFoundException;
import com.hexaware.hexacliq.service.ProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.*;

@Repository
public interface IProjectRepository extends JpaRepository<Project, Integer> {

}
