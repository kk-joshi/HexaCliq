package com.hexaware.hexacliq.dto;

import java.util.List;

public class AttendanceDto {

    List<String> formattedDates;
    private Integer userId;
    private String category;
    private Double hours;

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }


    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public List<String> getFormattedDates() {
        return formattedDates;
    }

    public void setFormattedDates(List<String> markedDates) {
        this.formattedDates = markedDates;
    }

    public Double getHours() {
        return hours;
    }

    public void setHours(Double hours) {
        this.hours = hours;
    }


}

