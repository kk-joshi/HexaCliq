package com.hexaware.hexacliq.report;

import com.hexaware.hexacliq.dto.Attendance;
import com.hexaware.hexacliq.dto.CategoryEnum;
import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFColor;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Function;
import java.util.stream.Collectors;

public class MonthlyReportExporter {
    private static final String DATE_HEADER_FORMAT = "d-mmm";
    private final Map<Integer, List<Attendance>> attendanceMap;
    private final String month;
    private final byte[] HEADER_COLOR1 = new byte[]{10, 125, 125};
    private final String[] headers = {"Employee ID", "Employee Name", "Project", "Location"};
    private final XSSFWorkbook workbook;
    List<LocalDate> dates;
    private XSSFSheet sheet;
    private Map<Integer, LocalDate> dateMap;

    public MonthlyReportExporter(Map<Integer, List<Attendance>> attendanceMap, String month) {
        workbook = new XSSFWorkbook();
        this.attendanceMap = attendanceMap;
        this.month = month;
        populateDates();
    }

    public void export(HttpServletResponse response) throws IOException {
        sheet = workbook.createSheet("Monthly Report - " + month);
        writeHeaderLine();
        writeDataLines();

        ServletOutputStream outputStream = response.getOutputStream();
        workbook.write(outputStream);
        workbook.close();
        outputStream.close();
    }

    private void populateDates() {
        dateMap = new LinkedHashMap<>();
        dates = getMonthDates();
        dateMap = dates.stream().collect(Collectors.toMap(LocalDate::getDayOfMonth, Function.identity(), (o, n) -> n, LinkedHashMap::new));
    }

    private List<LocalDate> getMonthDates() {
        LocalDate fromDate = getFirstDayOfMonth();
        LocalDate toDate = getLastDayOfMonth(fromDate);

        List<LocalDate> dates = new ArrayList<>(31);
        while (fromDate.equals(toDate)) {
            dates.add(fromDate);
            fromDate = fromDate.plusDays(1);
        }
        return dates;
    }

    private LocalDate getFirstDayOfMonth() {
        return LocalDate.parse("1-" + month, DateTimeFormatter.ofPattern("d-M-yyyy"));
    }

    private LocalDate getLastDayOfMonth(LocalDate localDate) {
        return localDate.withDayOfMonth(localDate.getMonth().length(localDate.isLeapYear()));
    }

    private void writeHeaderLine() {
        Row title = sheet.createRow(0);
        CellStyle titleStyle = workbook.createCellStyle();
        titleStyle.setFont(getTitleFont());
        createCell(title, 0, "Monthly Attendance Report for " + month, titleStyle);

        Row row = sheet.createRow(1);
        CellStyle headerStyle = workbook.createCellStyle();
        headerStyle.setFont(getHeaderFont());
        headerStyle.setBorderBottom(BorderStyle.DOUBLE);
        headerStyle.setBorderLeft(BorderStyle.DOUBLE);
        headerStyle.setBorderRight(BorderStyle.DOUBLE);
        headerStyle.setBorderBottom(BorderStyle.DOUBLE);

        CellStyle dateStyle = workbook.createCellStyle();
        dateStyle.setFont(getHeaderFont());
        dateStyle.setDataFormat(workbook.getCreationHelper().createDataFormat().getFormat(DATE_HEADER_FORMAT));

        AtomicInteger cnt = new AtomicInteger();

        Arrays.stream(headers).forEach(header -> createCell(row, cnt.getAndIncrement(), header, headerStyle));
        dateMap.values().forEach(date -> createCell(row, cnt.getAndIncrement(), date, headerStyle));
        Arrays.stream(CategoryEnum.values()).map(CategoryEnum::getShortName).forEach(attendanceType -> createCell(row, cnt.getAndIncrement(), attendanceType, headerStyle));
    }

    private XSSFFont getTitleFont() {
        XSSFFont font = workbook.createFont();
        font.setBold(true);
        font.setFontHeight(25);
        return font;
    }

    private XSSFFont getHeaderFont() {
        XSSFFont font = workbook.createFont();
        font.setBold(true);
        font.setFontHeight(16);
        font.setColor(new XSSFColor(HEADER_COLOR1));
        return font;
    }

    private void writeDataLines() {
        AtomicInteger rowCount = new AtomicInteger(2);
        CellStyle style = workbook.createCellStyle();
        XSSFFont font = workbook.createFont();
        font.setFontHeight(14);
        style.setFont(font);

        attendanceMap.forEach((userId, attendanceData) -> {
            Row row = workbook.createSheet().createRow(rowCount.getAndIncrement());
            int colCount = 0;

//            TODO: User object is coming as null. Need to fix it.
//            User user = attendanceData.get(0).getUser();
//            createCell(row, colCount++, user.getEmpId(), style);
//            createCell(row, colCount++, user.getFirstName() + " " + user.getLastName(), style);
            createCell(row, colCount++, "User-id " + attendanceData.get(0).getUserId(), style);
            createCell(row, colCount++, "BR-Project " + attendanceData.get(0).getUserId(), style);
            createCell(row, colCount++, "BR-Project", style);
            createCell(row, colCount++, "Pune", style);
            final int COL_OFF_SET = colCount;
            dateMap.forEach((index, localDate) -> {
                CategoryEnum category = attendanceData.stream().filter(a -> localDate.equals(a.getMarkedDate())).findAny().map(Attendance::getCategory).orElse(CategoryEnum.OTHER);
                createCell(row, index + COL_OFF_SET, category.getShortName(), style);
            });
        });
    }

    private void createCell(Row row, int columnCount, Object value, CellStyle style) {
        sheet.autoSizeColumn(columnCount);
        Cell cell = row.createCell(columnCount);
        if (value instanceof Integer) {
            cell.setCellValue((Integer) value);
        } else if (value instanceof Boolean) {
            cell.setCellValue((Boolean) value);
        } else {
            cell.setCellValue((String) value);
        }
        cell.setCellStyle(style);
    }
}
