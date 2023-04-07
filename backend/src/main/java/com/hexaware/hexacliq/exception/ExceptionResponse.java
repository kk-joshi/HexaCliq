package com.hexaware.hexacliq.exception;

import lombok.Data;

import java.util.List;

@Data
public class ExceptionResponse {
	
	private String excepion;
	private String msg;
	private List<String> details;
	
	public ExceptionResponse(String msg, List<String> details) {
		super();
		this.msg = msg;
		this.details = details;
	}
	
	
}
