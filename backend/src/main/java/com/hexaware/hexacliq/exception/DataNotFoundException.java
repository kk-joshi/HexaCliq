package com.hexaware.hexacliq.exception;

import com.hexaware.hexacliq.utils.Constants;

public class DataNotFoundException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;


	public DataNotFoundException() {
		super(Constants.DATA_NOT_FOUND);
	}
	
	
	public DataNotFoundException(String msg){
		super(msg);
	}
	
}
