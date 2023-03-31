package com.hexaware.hexacliq.service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hexaware.hexacliq.dao.IAttendanceRepository;
import com.hexaware.hexacliq.dto.Attendance;
import com.hexaware.hexacliq.dto.Attendance.CategoryEnum;
import com.hexaware.hexacliq.dto.AttendanceDto;

@Service

public class AttendanceService {

	@Autowired
	IAttendanceRepository attendanceRepository;

	public String submitAttendance(AttendanceDto form) {

		System.out.println(form.getCategory());

		List<String> dateList = new ArrayList<>();
		dateList = form.getFormattedDates();
		for (String s : dateList) {
			Attendance attendance = new Attendance();
			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
			String date = "16/08/2016";
			// convert String to LocalDate
			LocalDate localDate = LocalDate.parse(s, formatter);
			attendance.setMarkedDate(localDate);
			attendance.setUserId(2000080111);
			attendance.setCreatedTime(new Date());
			attendance.setModifiedTime(new Date());
			attendance.setCategory(CategoryEnum.valueOf(form.getCategory()));
			attendanceRepository.save(attendance);
		}
		return "Attendance saved";
	}

	public Map<String, List<LocalDate>> getMarkedAttendance(Integer userId, String month) {
		String[] monthYear = month.split("-");
		List<Attendance> attendaceList = attendanceRepository.findMonthAttendance(userId, monthYear[0], monthYear[1]);
		return attendaceList.stream().collect(Collectors.groupingBy(a -> String.valueOf(a.getCategory().ordinal()), HashMap::new,
				Collectors.mapping(Attendance::getMarkedDate, Collectors.toList())));
	}

}
