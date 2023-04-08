package com.hexaware.hexacliq.exception;

import com.hexaware.hexacliq.utils.Constants;

public class UserNotFoundException extends RuntimeException {

    /**
     *
     */
    private static final long serialVersionUID = 1L;


    public UserNotFoundException() {
        super(Constants.USER_NOT_FOUND);
    }


    public UserNotFoundException(String msg) {
        super(msg);
    }

}
