package com.hexaware.hexacliq.dto;

import java.util.Date;
import java.util.List;

public class AttendanceDto {
	
	private Integer userId;

	private String categoryId;
	
	List<String> markedDates;
	
	private Double hours;

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public String getCategoryId() {
		return categoryId;
	}

	public void setCategoryId(String categoryId) {
		this.categoryId = categoryId;
	}

	public List<String> getMarkedDates() {
		return markedDates;
	}

	public void setMarkedDates(List<String> markedDates) {
		this.markedDates = markedDates;
	}

	public Double getHours() {
		return hours;
	}

	public void setHours(Double hours) {
		this.hours = hours;
	}
	
	
}

