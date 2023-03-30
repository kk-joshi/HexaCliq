package com.hexaware.hexacliq.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.hexaware.hexacliq.dto.Attendance;
import com.hexaware.hexacliq.dto.User;
import java.util.*;

public interface IAttendanceRepository extends JpaRepository<Attendance, Integer> {
    String GET_USER_DATA_FOR_MONTH = "Select from Attendance where userId = :UserId and month(markedDate) = :Month and year(markedDate) = :Year";

    @Query(GET_USER_DATA_FOR_MONTH, nativeQuery = true)
    public List<Attendance> findMonthAttendance(@Param(name = "UserId") String userId, @Param(name = "Month") String month, @Param(name = "Year") String year);

}
