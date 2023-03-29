package com.hexaware.hexacliq.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hexaware.hexacliq.dto.AttendanceDto;
import com.hexaware.hexacliq.service.AttendanceService;

@RestController
@RequestMapping("/attendance")
public class AttendanceController {

	@Autowired
	AttendanceService attendanceService;
	
	@PostMapping("/submit")
	public ResponseEntity<String> post( AttendanceDto form){
		String response = attendanceService.submitAttendance(form);
		 return new ResponseEntity<String>(response,HttpStatus.CREATED);
	}
}
