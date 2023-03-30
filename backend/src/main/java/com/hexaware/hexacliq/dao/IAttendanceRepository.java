package com.hexaware.hexacliq.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.hexaware.hexacliq.dto.Attendance;
import java.util.*;

public interface IAttendanceRepository extends JpaRepository<Attendance, Integer> {
    String GET_USER_DATA_FOR_MONTH = "Select from Attendance where userId = :UserId and month(markedDate) = :Month and year(markedDate) = :Year";

    @Query(value = GET_USER_DATA_FOR_MONTH, nativeQuery = true)
    public List<Attendance> findMonthAttendance(@Param(value = "UserId") String userId, @Param(value = "Month") String month, @Param(value = "Year") String year);

}
