package com.hegemony.arraymi.captcha.service;

import org.springframework.http.HttpStatus;

public class AppException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4096922251675471502L;

	private HttpStatus status;

	public AppException(HttpStatus status, String msg) {
		super(msg);
		this.status = status;
	}

	public HttpStatus getStatus() {
		return status;
	}

	public void setStatus(HttpStatus status) {
		this.status = status;
	}

}
