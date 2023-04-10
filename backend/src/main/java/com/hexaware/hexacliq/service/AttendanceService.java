package com.hexaware.hexacliq.service;

import com.hexaware.hexacliq.dao.IAttendanceRepository;
import com.hexaware.hexacliq.dto.Attendance;
import com.hexaware.hexacliq.dto.AttendanceDto;
import com.hexaware.hexacliq.dto.CategoryEnum;
import com.hexaware.hexacliq.dto.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
public class AttendanceService {
    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("dd/MM/yyyy");
    private static final DateTimeFormatter MONTH_FORMATTER = DateTimeFormatter.ofPattern("MM-yyyy");
    @Autowired
    IAttendanceRepository attendanceRepository;

    /**
     * Mark the attendance of the consultant.
     *
     * @param attendanceDto -
     * @return
     */
    public String submitAttendance(AttendanceDto attendanceDto) {
        for (String s : attendanceDto.getFormattedDates()) {
            Attendance attendance = new Attendance();
            LocalDate localDate = LocalDate.parse(s, DATE_FORMATTER);
            attendance.setMarkedDate(localDate);
            attendance.setMonth(localDate.format(MONTH_FORMATTER));
            attendance.setUserId(attendanceDto.getUserId());
            User user = new User();
            user.setUserId(attendanceDto.getUserId());
            attendance.setCreatedTime(LocalDate.now());
            attendance.setModifiedTime(LocalDate.now());
            attendance.setCategory(CategoryEnum.getCategoryBy(attendanceDto.getCategory()));
            attendanceRepository.save(attendance);
        }
        return "Attendance saved";
    }

    public Map<String, List<LocalDate>> getMarkedAttendance(Integer userId, String month) {
        List<Attendance> attendanceList = attendanceRepository.findMonthlyAttendanceOfUser(userId, month);
        return attendanceList.stream().collect(Collectors.groupingBy(a -> String.valueOf(a.getCategory().ordinal()), HashMap::new,
                Collectors.mapping(Attendance::getMarkedDate, Collectors.toList())));
    }

    public Map<Integer, List<Attendance>> getMarkedAttendance(String month) {
        List<Attendance> attendanceList = attendanceRepository.findAllUserMonthlyData(month);
        return attendanceList.stream().collect(Collectors.groupingBy(Attendance::getUserId, HashMap::new,
                Collectors.mapping(Function.identity(), Collectors.toList())));
    }

}
