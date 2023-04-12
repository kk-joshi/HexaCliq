package com.hexaware.hexacliq.report;

import com.hexaware.hexacliq.dao.IUserRepository;
import com.hexaware.hexacliq.dto.Attendance;
import com.hexaware.hexacliq.dto.CategoryEnum;
import com.hexaware.hexacliq.dto.User;
import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoField;
import java.time.temporal.TemporalAccessor;
import java.time.temporal.TemporalQuery;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Function;
import java.util.stream.Collectors;

import static com.hexaware.hexacliq.utils.Constants.*;

@Slf4j
public class MonthlyReportExporter {
    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("d-MMM");

    private IUserRepository userRepository;
    private final Map<Integer, List<Attendance>> attendanceMap;
    private final String month;
    private final int[] counts = new int[CategoryEnum.values().length];

    private final String[] headers = {"Employee ID", "Employee Name", "Project", "Location"};
    private final XSSFWorkbook workbook;
    private final AtomicInteger rowCount = new AtomicInteger();
    List<LocalDate> dates;
    private XSSFSheet sheet;
    private Map<Integer, LocalDate> dateMap;

    private Map<CategoryEnum, CellStyle> textStyleMap;
    private Map<CategoryEnum, CellStyle> numericStyleMap;
    private List<String> weekEnds;
    private CellStyle numericStyle;

    public MonthlyReportExporter(IUserRepository userRepository, Map<Integer, List<Attendance>> attendanceMap, String month) {
        workbook = new XSSFWorkbook();
        this.userRepository = userRepository;
        this.attendanceMap = attendanceMap;
        this.month = month;
        populateDates();
    }

    public void export(HttpServletResponse response) throws IOException {
        createSheet("Monthly Report - " + month);
        writeHeaderLine();
        writeDataLines();
        exportReport(response);
    }

    private void createSheet(String sheetName) {
        sheet = workbook.createSheet(sheetName);
    }

    private void exportReport(HttpServletResponse response) throws IOException {
        try(ServletOutputStream outputStream = response.getOutputStream()) {
            workbook.write(outputStream);
            workbook.close();
        }
    }

    private void populateDates() {
        dateMap = new LinkedHashMap<>();
        dates = getMonthDates();
        weekEnds = dates.stream().filter(d -> d.query(new IsWeekendQuery())).map(DATE_FORMATTER::format).collect(Collectors.toList());
        dateMap = dates.stream().sorted().collect(Collectors.toMap(LocalDate::getDayOfMonth, Function.identity(), (o, n) -> n, LinkedHashMap::new));
    }

    private List<LocalDate> getMonthDates() {
        LocalDate fromDate = getFirstDayOfMonth();
        LocalDate toDate = getLastDayOfMonth(fromDate);

        List<LocalDate> dates = new ArrayList<>(31);
        while (toDate.isAfter(fromDate)) {
            dates.add(fromDate);
            fromDate = fromDate.plusDays(1);
        }
        dates.sort(Comparator.naturalOrder());
        return dates;
    }

    private LocalDate getFirstDayOfMonth() {
        return LocalDate.parse("1-" + month, DateTimeFormatter.ofPattern("d-M-yyyy"));
    }

    private LocalDate getLastDayOfMonth(LocalDate localDate) {
        return localDate.withDayOfMonth(localDate.getMonth().length(localDate.isLeapYear()));
    }

    private void writeHeaderLine() {
        sheet.addMergedRegion(CellRangeAddress.valueOf("A1:AO1"));
        sheet.addMergedRegion(CellRangeAddress.valueOf("A2:AO2"));
        sheet.addMergedRegion(CellRangeAddress.valueOf("A3:AO3"));
        sheet.createRow(rowCount.getAndIncrement());
        Row title = sheet.createRow(rowCount.getAndIncrement());

        CellStyle titleStyle = getHeaderStyle(getTitleFont());
        titleStyle.setAlignment(HorizontalAlignment.LEFT);
        createCell(title, 0, "Monthly Attendance Report for " + month, titleStyle);
        sheet.createRow(rowCount.getAndIncrement());

        Row header = sheet.createRow(rowCount.getAndIncrement());
        CellStyle headerStyle = getHeaderStyle(getHeaderFont());
        CellStyle headerWeekendStyle = getHeaderStyle(getWeekHeaderFont());
        AtomicInteger cnt = new AtomicInteger();
        Arrays.stream(headers).forEach(h -> createCell(header, cnt.getAndIncrement(), h, headerStyle));
        dateMap.values().stream().map(DATE_FORMATTER::format).forEach(date -> {
            CellStyle cellStyle = weekEnds.contains(date) ? headerWeekendStyle : headerStyle;
            createCell(header, cnt.getAndIncrement(), date, cellStyle);
        });

        Arrays.stream(CategoryEnum.values()).map(CategoryEnum::getShortName).forEach(attendanceType -> createCell(header, cnt.getAndIncrement(), attendanceType, headerStyle));
        createCell(header, cnt.getAndIncrement(), "Total", headerStyle);
    }

    private void writeDataLines() {
        populateTextStyles();
        numericStyle = getNumericStyle();

        attendanceMap.forEach((userId, attendanceData) -> {
            resetCounts();
            Row dataRow = sheet.createRow(rowCount.getAndIncrement());
            AtomicInteger colCount = new AtomicInteger();
            User user = userRepository.findById(userId).orElse(new User());
            createCell(dataRow, colCount.getAndIncrement(), user.getEmpId(), textStyleMap.get(CategoryEnum.FULL_DAY));
            createCell(dataRow, colCount.getAndIncrement(), user.getFirstName() + " " + user.getLastName(), textStyleMap.get(CategoryEnum.FULL_DAY));
            createCell(dataRow, colCount.getAndIncrement(), "BR-Project"+ user.getUserId() , textStyleMap.get(CategoryEnum.FULL_DAY));
            createCell(dataRow, colCount.getAndIncrement(), "Pune", textStyleMap.get(CategoryEnum.FULL_DAY));
            dateMap.forEach((index, localDate) -> {
                CategoryEnum category = attendanceData.stream().filter(a -> localDate.equals(a.getMarkedDate())).findAny().map(Attendance::getCategory).orElse(CategoryEnum.OTHER);
                createCell(dataRow, colCount.getAndIncrement(), category.getShortName(), textStyleMap.get(category));
                counts[category.ordinal()]++;
            });
            Arrays.stream(counts).forEach(count ->  createCell(dataRow, colCount.getAndIncrement(), count, numericStyle));
            createCell(dataRow, colCount.getAndIncrement(),  Arrays.stream(counts).sum() , numericStyle);
        });
        sheet.createRow(rowCount.getAndIncrement());
    }

    private void resetCounts() {
        Arrays.stream(CategoryEnum.values()).map(Enum::ordinal).forEach(index -> counts[index] = 0);
    }

    private void populateTextStyles() {
        textStyleMap = Arrays.stream(CategoryEnum.values()).collect(Collectors.toMap(Function.identity(), this::getDataStyle));
    }

    private CellStyle getHeaderStyle(XSSFFont xssfFont) {
        CellStyle headerStyle = workbook.createCellStyle();
        headerStyle.setFont(xssfFont);
        headerStyle.setBorderTop(BorderStyle.DOUBLE);
        headerStyle.setBorderLeft(BorderStyle.DOUBLE);
        headerStyle.setBorderRight(BorderStyle.DOUBLE);
        headerStyle.setBorderBottom(BorderStyle.DOUBLE);
        headerStyle.setAlignment(HorizontalAlignment.CENTER);
        return headerStyle;
    }

    private CellStyle getDataStyle(CategoryEnum categoryEnum) {
        CellStyle dataStyle = workbook.createCellStyle();
        dataStyle.setFont(getDataLineFont(categoryEnum));
        dataStyle.setBorderBottom(BorderStyle.THIN);
        dataStyle.setBorderLeft(BorderStyle.THIN);
        dataStyle.setBorderRight(BorderStyle.THIN);
        dataStyle.setBorderTop(BorderStyle.THIN);
        dataStyle.setAlignment(HorizontalAlignment.CENTER);
        return dataStyle;
    }

    private CellStyle getNumericStyle() {
        CellStyle dataStyle = workbook.createCellStyle();
        dataStyle.setFont(getDataLineFont(CategoryEnum.FULL_DAY));
        dataStyle.setBorderBottom(BorderStyle.THIN);
        dataStyle.setBorderLeft(BorderStyle.THIN);
        dataStyle.setBorderRight(BorderStyle.THIN);
        dataStyle.setBorderTop(BorderStyle.THIN);
        dataStyle.setAlignment(HorizontalAlignment.CENTER);
        return dataStyle;
    }

    private XSSFFont getTitleFont() {
        XSSFFont font = workbook.createFont();
        font.setBold(true);
        font.setFontHeight(20);
        font.setColor(COLOR_OLIVE);
        return font;
    }

    private XSSFFont getHeaderFont() {
        XSSFFont font = workbook.createFont();
        font.setBold(true);
        font.setFontHeight(11);
        font.setColor(COLOR_NAVY);
        return font;
    }

    private XSSFFont getWeekHeaderFont() {
        XSSFFont font = workbook.createFont();
        font.setBold(true);
        font.setFontHeight(11);
        font.setColor(COLOR_MAROON);
        return font;
    }

    private XSSFFont getDataLineFont(CategoryEnum category) {
        XSSFFont font = workbook.createFont();
        font.setBold(true);
        font.setFontHeight(10);
        font.setColor(category.getColor());
        return font;
    }

    private void createCell(Row row, int columnCount, Object value, CellStyle style) {
        sheet.autoSizeColumn(columnCount);
        Optional<CellStyle> custom = Optional.empty();
        Cell cell = row.createCell(columnCount);
        if (value instanceof Integer) {
            cell.setCellValue((Integer) value);
            custom = Optional.of(workbook.createCellStyle());
            custom.ifPresent(
                    c -> {
                        c.cloneStyleFrom(style);
                        c.setAlignment(HorizontalAlignment.RIGHT);
                    });
            style.setAlignment(HorizontalAlignment.RIGHT);
        } else if (value instanceof Boolean) {
            cell.setCellValue((Boolean) value);
        } else {
            cell.setCellValue(String.valueOf(value));
        }
        cell.setCellStyle(custom.orElse(style));
        log.info("Writing a cell {} - {}", columnCount, value);
    }
    static class IsWeekendQuery implements TemporalQuery<Boolean> {
        @Override
        public Boolean queryFrom(TemporalAccessor temporal) {
            return temporal.get(ChronoField.DAY_OF_WEEK) >= 6;
        }
    }
}
