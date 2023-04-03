package com.hexaware.hexacliq.service;

import com.hexaware.hexacliq.dao.IAttendanceRepository;
import com.hexaware.hexacliq.dto.Attendance;
import com.hexaware.hexacliq.dto.Attendance.CategoryEnum;
import com.hexaware.hexacliq.dto.AttendanceDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.CrossOrigin;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;
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
            attendance.setCategory(getCategoryBy(form.getCategory()));
            attendanceRepository.save(attendance);
        }
        return "Attendance saved";
    }

    private CategoryEnum getCategoryBy(String category) {
       try {
           return CategoryEnum.valueOf(category);
       } catch (Exception e) {
           return Arrays.stream(CategoryEnum.values()).filter(c -> category.equals("" + c.ordinal())).findFirst().orElse(CategoryEnum.FULL_DAY);
       }
    }

    public Map<String, List<LocalDate>> getMarkedAttendance(Integer userId, String month) {
        String[] monthYear = month.split("-");
        List<Attendance> attendaceList = attendanceRepository.findMonthAttendance(userId, monthYear[0], monthYear[1]);
        return attendaceList.stream().collect(Collectors.groupingBy(a -> String.valueOf(a.getCategory().ordinal()), HashMap::new,
                Collectors.mapping(Attendance::getMarkedDate, Collectors.toList())));
    }

}
