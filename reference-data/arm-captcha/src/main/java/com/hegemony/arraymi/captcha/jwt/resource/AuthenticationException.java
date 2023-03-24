package com.hegemony.arraymi.captcha.jwt.resource;

public class AuthenticationException extends RuntimeException {
	/**
	 * 
	 */
	private static final long serialVersionUID = -6112001962833453136L;

	public AuthenticationException(String message, Throwable cause) {
		super(message, cause);
	}
}
