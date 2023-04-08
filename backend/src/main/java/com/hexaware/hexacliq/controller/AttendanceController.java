package com.hexaware.hexacliq.controller;


import com.hexaware.hexacliq.dto.AttendanceDto;
import com.hexaware.hexacliq.service.AttendanceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
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
    public ResponseEntity<String> submitAttendance(@RequestBody AttendanceDto form) {
        String response = attendanceService.submitAttendance(form);
        return new ResponseEntity<String>(response, HttpStatus.CREATED);
    }

    @GetMapping("/{month}")
    public ResponseEntity<Map> getMarkedAttendance(@PathVariable("month") String month, Principal principal) {
        Map<String, List<LocalDate>> response = attendanceService.getMarkedAttendance(2000080111, month);
        return new ResponseEntity<Map>(response, HttpStatus.CREATED);
    }

}
