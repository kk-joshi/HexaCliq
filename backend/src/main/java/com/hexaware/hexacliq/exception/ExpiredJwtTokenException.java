package com.hexaware.hexacliq.exception;

import com.hexaware.hexacliq.utils.Constants;

public class ExpiredJwtTokenException extends RuntimeException {

    /**
     *
     */
    private static final long serialVersionUID = 1L;


    public ExpiredJwtTokenException() {
        super(Constants.JWT_TOKEN_EXPIRED);
    }


    public ExpiredJwtTokenException(String msg) {
        super(msg);
    }

}
