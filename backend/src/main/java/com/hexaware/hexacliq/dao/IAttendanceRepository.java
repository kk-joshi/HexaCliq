package com.hexaware.hexacliq.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.hexaware.hexacliq.dto.Attendance;
import java.util.*;

public interface IAttendanceRepository extends JpaRepository<Attendance, Integer> {
    String GET_USER_DATA_FOR_MONTH = "Select *  from attendance_master where user_id = :UserId and month(marked_date) = :Month and year(marked_date) = :Year";
    

    @Query(value = GET_USER_DATA_FOR_MONTH, nativeQuery = true)
    public List<Attendance> findMonthAttendance(@Param("UserId") Integer userId, @Param("Month") String month, @Param("Year") String year);

}
