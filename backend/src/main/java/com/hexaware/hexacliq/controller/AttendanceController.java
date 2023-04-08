package com.hexaware.hexacliq.controller;


import com.hexaware.hexacliq.dto.AttendanceDto;
import com.hexaware.hexacliq.dto.User;
import com.hexaware.hexacliq.service.AttendanceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/attendance")
public class AttendanceController {
    @Autowired
    AttendanceService attendanceService;

    @PostMapping
    public ResponseEntity<String> submitAttendance(@RequestBody AttendanceDto form, Authentication authentication) {
        User user = (User) authentication.getPrincipal();
        form.setUserId(Math.toIntExact(user.getUserId()));
        String response = attendanceService.submitAttendance(form);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @GetMapping("/{month}")
    public ResponseEntity<Map<String, List<LocalDate>>> getMarkedAttendance(@PathVariable("month") String month, Authentication authentication) {
        User user = (User) authentication.getPrincipal();
        Map<String, List<LocalDate>> response = attendanceService.getMarkedAttendance(Math.toIntExact(user.getUserId()), month);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

}
