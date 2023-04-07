package com.hexaware.hexacliq.controller;


import com.hexaware.hexacliq.dto.Attendance;
import com.hexaware.hexacliq.report.MonthlyReportExporter;
import com.hexaware.hexacliq.service.AttendanceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Map;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/report")
public class ReportController {
    @Autowired
    AttendanceService attendanceService;

    @GetMapping("excel/{month}")
    public void exportToExcel(HttpServletResponse response, @PathVariable("month") String month) throws IOException {
        response.setContentType("application/octet-stream");
        String headerKey = "Content-Disposition";
        String headerValue = "attachment; filename=Monthly-Attendance-Report-" + month + ".xlsx";
        response.setHeader(headerKey, headerValue);
        Map<Integer, List<Attendance>> attendances = attendanceService.getMarkedAttendance(month);
        new MonthlyReportExporter(attendances, month).export(response);
    }

}
