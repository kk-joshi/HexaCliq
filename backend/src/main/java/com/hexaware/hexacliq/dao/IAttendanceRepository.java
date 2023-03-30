package com.hexaware.hexacliq.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.hexaware.hexacliq.dto.Attendance;
import com.hexaware.hexacliq.dto.User;

public interface IAttendanceRepository extends JpaRepository<Attendance, Integer> {
	

}
