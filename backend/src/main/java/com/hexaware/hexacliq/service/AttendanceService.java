package com.hexaware.hexacliq.service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import com.hexaware.hexacliq.dto.Attendance;
import com.hexaware.hexacliq.dto.AttendanceDto;

public class AttendanceService {

	public String submitAttendance(List<AttendanceDto> attendancelist) {
		
		Attendance attendance= new Attendance();
		for(AttendanceDto a:attendancelist) {
			List<String> dateList= new ArrayList<>();
			dateList= a.getMarkedDates();
			for(String s:dateList) {
			  DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
			  String date = "16/08/2016";
			  //convert String to LocalDate
			  LocalDate localDate = LocalDate.parse(date, formatter);	
				/*
				 * attendance.setCategoryId(a.getCategoryId());
				 * attendance.setHours(a.getHours()); attendance.setMarkedDates(dateList);
				 */
			  
			}
		}
		return null;
	}

}
