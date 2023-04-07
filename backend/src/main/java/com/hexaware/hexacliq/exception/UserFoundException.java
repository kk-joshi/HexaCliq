package com.hexaware.hexacliq.exception;


import com.hexaware.hexacliq.utils.Constants;

public class UserFoundException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;


	public UserFoundException() {
		super(Constants.USER_ALREADY_REGISTER);
	}
	
	
	public UserFoundException(String msg){
		super(msg);
	}
	
}
