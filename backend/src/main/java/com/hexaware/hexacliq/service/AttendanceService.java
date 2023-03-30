package com.hexaware.hexacliq.service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import com.hexaware.hexacliq.dao.IAttendanceRepository;
import com.hexaware.hexacliq.dto.Attendance;
import com.hexaware.hexacliq.dto.Attendance.CategoryEnum;
import com.hexaware.hexacliq.dto.AttendanceDto;

public class AttendanceService {
	
	@Autowired
	IAttendanceRepository attendanceRepository;

	public String submitAttendance(List<AttendanceDto> attendancelist) {
		Attendance attendance= new Attendance();
		for(AttendanceDto a:attendancelist) {
			List<String> dateList= new ArrayList<>();
			dateList= a.getMarkedDates();
			for(String s:dateList) {
			  DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
			  String date = "16/08/2016";
			  //convert String to LocalDate
			  LocalDate localDate = LocalDate.parse(date, formatter);	
			  attendance.setMarkedDate(localDate);
			  attendance.setCategory(CategoryEnum.valueOf(a.getCategory()));
			  attendanceRepository.save(attendance);
			}
		}
		return null;
	}

	public Map<String, List<LocalDate>> getMarkedAttendance(String userId, String month) {
		String[] monthYear = month.split("/");
		List<Attendance> attendaceList = attendanceRepository.findMonthAttendance(userId, monthYear[0], monthYear[1]);
//List<Att> atts = new ArrayList<>();
//atts.stream().collect(Collectors.groupingBy(Att::getName, HashMap::new, Collectors.mapping(Att::getLocalDate, Collectors.toList())));
		return null;
	}

}
