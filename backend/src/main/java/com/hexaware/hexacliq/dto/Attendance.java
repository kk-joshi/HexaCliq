package com.hexaware.hexacliq.dto;

import java.time.LocalDate;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "attendance_master")
public class Attendance {
    @Id
    @GeneratedValue
    private Integer attendanceId;
    
    @Column(name = "attendance_name")
    private String attendanceName;
    
    @Column(name = "marked_date")
    private LocalDate markedDate;
    
    @Column(name = "hours")
    private Double hours;
    
    @Column(name = "created_time")
    private Date createdTime;
    
    @Column(name = "modified_time")
    private Date modifiedTime;
    
    @Column(name = "created_by")
    private String createdBy;
    
    @Column(name = "modified_by")
    private String modifiedBy;

	public Integer getProjectId() {
		return attendanceId;
	}

	public void setProjectId(Integer attendanceId) {
		this.attendanceId = attendanceId;
	}

	public String getProjectName() {
		return attendanceName;
	}

	public void setProjectName(String attendanceName) {
		this.attendanceName = attendanceName;
	}

	
	public Date getCreatedTime() {
		return createdTime;
	}

	public void setCreatedTime(Date createdTime) {
		this.createdTime = createdTime;
	}

	public Date getModifiedTime() {
		return modifiedTime;
	}

	public void setModifiedTime(Date modifiedTime) {
		this.modifiedTime = modifiedTime;
	}

	public String getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	public String getModifiedBy() {
		return modifiedBy;
	}

	public void setModifiedBy(String modifiedBy) {
		this.modifiedBy = modifiedBy;
	}
    
    
}
