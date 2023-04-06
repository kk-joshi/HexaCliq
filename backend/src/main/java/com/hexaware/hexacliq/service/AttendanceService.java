package com.hexaware.hexacliq.service;

import com.hexaware.hexacliq.dao.IAttendanceRepository;
import com.hexaware.hexacliq.dto.Attendance;
import com.hexaware.hexacliq.dto.AttendanceDto;
import com.hexaware.hexacliq.dto.CategoryEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
public class AttendanceService {

    @Autowired
    IAttendanceRepository attendanceRepository;

    public String submitAttendance(AttendanceDto form) {
        for (String s : form.getFormattedDates()) {
            Attendance attendance = new Attendance();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
            LocalDate localDate = LocalDate.parse(s, formatter);
            attendance.setMarkedDate(localDate);
            attendance.setUserId(2000080111);
            attendance.setCreatedTime(new Date());
            attendance.setModifiedTime(new Date());
            attendance.setCategory(CategoryEnum.getCategoryBy(form.getCategory()));
            attendanceRepository.save(attendance);
        }
        return "Attendance saved";
    }

    public Map<String, List<LocalDate>> getMarkedAttendance(Integer userId, String month) {
        String[] monthYear = month.split("-");
        List<Attendance> attendaceList = attendanceRepository.findMonthlyAttendanceOfUser(userId, monthYear[0], monthYear[1]);
        return attendaceList.stream().collect(Collectors.groupingBy(a -> String.valueOf(a.getCategory().ordinal()), HashMap::new,
                Collectors.mapping(Attendance::getMarkedDate, Collectors.toList())));
    }

    public Map<Integer, List<Attendance>> getMarkedAttendance(String month) {
        String[] monthYear = month.split("-");
        List<Attendance> attendaceList = attendanceRepository.findAllUserMonthlyData(monthYear[0], monthYear[1]);
        return attendaceList.stream().collect(Collectors.groupingBy(Attendance::getUserId, HashMap::new,
                Collectors.mapping(Function.identity(), Collectors.toList())));
    }

}
