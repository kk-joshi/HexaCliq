package com.hexaware.hexacliq.dto;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@Entity
@Table(name = "attendance_master",
        indexes = { @Index(columnList = "month"), @Index(columnList = "user_id")},
        uniqueConstraints = @UniqueConstraint(columnNames = {"user_id", "marked_date", "category"}))
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
