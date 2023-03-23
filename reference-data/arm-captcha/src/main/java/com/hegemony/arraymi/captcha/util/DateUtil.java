package com.hegemony.arraymi.captcha.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDate;
import java.time.Period;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Calendar;
import java.util.Date;

import org.springframework.http.HttpStatus;

import com.hegemony.arraymi.captcha.service.AppException;

public class DateUtil {

	public static final String DATE_PATTERN = "dd-MM-yyyy";

	public static Date getDate(String dateString) {
		SimpleDateFormat formatter = new SimpleDateFormat(DATE_PATTERN);
		try {
			return formatter.parse(dateString);
		} catch (ParseException e) {
			throw new AppException(HttpStatus.BAD_REQUEST, "Please provide valid date!");
		}
	}

	public static String getDate(Date date) {
		SimpleDateFormat formatter = new SimpleDateFormat(DATE_PATTERN);
		return formatter.format(date);
	}

	public static String getDateTomorrowDateStr() {
		Calendar cal = Calendar.getInstance();
		cal.setTime(new Date());
		cal.add(Calendar.DATE, 1);
		return getDate(cal.getTime());
	}

	public static boolean isValidUser(String dob) {
		Date birthDate = getDate(dob);
		Instant instant = birthDate.toInstant();
		ZonedDateTime zone = instant.atZone(ZoneId.systemDefault());
		LocalDate givenDate = zone.toLocalDate();
		Period period = Period.between(givenDate, LocalDate.now());
		if (period.getYears() < AppConstants.LEGAL_AGE) {
			throw new AppException(HttpStatus.BAD_REQUEST, "You are under age for this website!");
		}
		return true;
	}

	public static String getDateForTodaysBirthday(Date date) {
		SimpleDateFormat formatter = new SimpleDateFormat("dd-MM");
		return formatter.format(date);
	}

}
