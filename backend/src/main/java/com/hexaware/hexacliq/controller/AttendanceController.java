package com.hexaware.hexacliq.controller;


import java.time.LocalDate;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hexaware.hexacliq.dto.AttendanceDto;
import com.hexaware.hexacliq.service.AttendanceService;

import io.jsonwebtoken.lang.Collections;

@RestController
@RequestMapping("/attendance")
public class AttendanceController {

	@Autowired
	AttendanceService attendanceService;
	
	@PostMapping
	public ResponseEntity<String> submitAttendance( @RequestBody AttendanceDto form){
		String response = attendanceService.submitAttendance(form);
		 return new ResponseEntity<String>(response,HttpStatus.CREATED);
	}
	
	@GetMapping("/{month}")
	public ResponseEntity<Map> getMarkedAttendance( @PathVariable("month") String month){
		Map<String, List<LocalDate>> response = attendanceService.getMarkedAttendance(2000080111,month);
		 return new ResponseEntity<Map>(response,HttpStatus.CREATED);
	}
	
}
