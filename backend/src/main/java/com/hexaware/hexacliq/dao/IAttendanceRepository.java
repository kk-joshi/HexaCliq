package com.hexaware.hexacliq.dao;

import com.hexaware.hexacliq.dto.Attendance;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IAttendanceRepository extends JpaRepository<Attendance, Integer> {
    String GET_USER_DATA_FOR_MONTH = "Select * from attendance_master where user_id = :UserId and month = :Month";
    String GET_ALL_USER_DATA_FOR_MONTH = "Select * from attendance_master where month = :Month";


    @Query(value = GET_USER_DATA_FOR_MONTH, nativeQuery = true)
    List<Attendance> findMonthlyAttendanceOfUser(@Param("UserId") Integer userId, @Param("Month") String month);

    @Query(value = GET_ALL_USER_DATA_FOR_MONTH, nativeQuery = true)
    List<Attendance> findAllUserMonthlyData(@Param("Month") String month);
}
