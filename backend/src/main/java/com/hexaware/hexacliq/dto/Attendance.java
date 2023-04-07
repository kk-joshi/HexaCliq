package com.hexaware.hexacliq.dto;

import java.time.LocalDate;
import jakarta.persistence.*;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "attendance_master", indexes = @Index(columnList = "month"))
public class Attendance {
    @Id
    @GeneratedValue
    private Integer attendanceId;
    
    @Column(name = "user_id")
    private Integer userId;



	@OneToOne
	private User user;

    @Column(name = "marked_date")
    private LocalDate markedDate;
    
    @Column(name = "created_time")
    private LocalDate createdTime;
    
    @Column(name = "modified_time")
    private LocalDate modifiedTime;
    
	@Enumerated(EnumType.ORDINAL)
    private CategoryEnum category;

	@Column(name = "month")
	private String month;
}
