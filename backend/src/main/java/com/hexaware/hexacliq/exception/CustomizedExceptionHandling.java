package com.hexaware.hexacliq.exception;

import com.hexaware.hexacliq.utils.Constants;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AuthorizationServiceException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@ControllerAdvice
public class CustomizedExceptionHandling extends ResponseEntityExceptionHandler {

    @ExceptionHandler(UserFoundException.class)
    public ResponseEntity<?> exceptionHandler(UserFoundException ex) {
        log.error("!!!!!!!!! Exception found user Found : {}", ex.getMessage());
        List<String> details = new ArrayList<>();
        details.add(ex.getLocalizedMessage());
        var error = new ExceptionResponse(Constants.USER_ALREADY_REGISTER, details);
        error.setExcepion(ex.getClass().getSimpleName());
        return new ResponseEntity<>(error, HttpStatus.CONFLICT);

    }

    @ExceptionHandler(UserNameFoundException.class)
    public ResponseEntity<?> exceptionHandler(UserNameFoundException ex) {
        log.error("!!!!!!!!! Exception found user Found : {}", ex.getMessage());
        List<String> details = new ArrayList<>();
        details.add(ex.getLocalizedMessage());
        var error = new ExceptionResponse(Constants.USER_NAME_ALREADY_EXIST, details);
        error.setExcepion(ex.getClass().getSimpleName());
        return new ResponseEntity<>(error, HttpStatus.CONFLICT);

    }

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<?> userNotExceptionHandler(UserNotFoundException ex) {
        log.error("!!!!!!!!! Exception found user Not Found : {}", ex.getMessage());
        List<String> details = new ArrayList<>();
        details.add(ex.getLocalizedMessage());
        var error = new ExceptionResponse(Constants.USER_NOT_FOUND, details);
        error.setExcepion(ex.getClass().getSimpleName());
        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);

    }

    @ExceptionHandler(DataNotFoundException.class)
    public ResponseEntity<Object> dataNotFoundExceptionHandler(DataNotFoundException ex, WebRequest request,
                                                               HttpServletResponse response) {
        log.error("!!!!!!!!! Exception found Data Not Found : {}", ex.getMessage());
        List<String> details = new ArrayList<>();
        details.add(ex.getLocalizedMessage());
        var error = new ExceptionResponse(Constants.INVALID_REQUEST, details);
        error.setExcepion(ex.getClass().getSimpleName());
        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<Object> duplicateDataException(DataIntegrityViolationException ex, WebRequest request,
                                                               HttpServletResponse response) {
        log.error("!!!!!!!!! Duplicate data insert : {}", ex.getMessage());
        List<String> details = new ArrayList<>();
        details.add("Duplicate data submitted.");
        var error = new ExceptionResponse(Constants.INVALID_REQUEST, details);
        error.setExcepion(ex.getClass().getSimpleName());
        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(UsernameNotFoundException.class)
    public ResponseEntity<Object> userNameNotFoundExceptionHandler(UsernameNotFoundException ex, WebRequest request,
                                                                   HttpServletResponse response) {
        log.error("!!!!!!!!! Exception found User Data Not Found : {}", ex.getMessage());
        List<String> details = new ArrayList<>();
        details.add(ex.getLocalizedMessage());
        var error = new ExceptionResponse(Constants.USER_NOT_FOUND, details);
        error.setExcepion(ex.getClass().getSimpleName());
        return new ResponseEntity<>(error, HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(AuthorizationServiceException.class)
    public ResponseEntity<Object> authorizationServiceException(AuthorizationServiceException ex, WebRequest request,
                                                                HttpServletResponse response) {
        log.error("!!!!!!!!! Exception found User Data Not Found : {}", ex.getMessage());
        List<String> details = new ArrayList<>();
        details.add(ex.getLocalizedMessage());
        var error = new ExceptionResponse(Constants.UNAUTHORIZED_SERVICE, details);
        error.setExcepion(ex.getClass().getSimpleName());
        return new ResponseEntity<>(error, HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Object> exceptionHandler(Exception ex, WebRequest request,
                                                   HttpServletResponse response) {
        log.error("!!!!!!!!! Exception : {}", ex.getMessage());
        List<String> details = new ArrayList<>();
        details.add(ex.getLocalizedMessage());
        var error = new ExceptionResponse(Constants.EXCEPTION + ex.getMessage(), details);
        error.setExcepion(ex.getClass().getSimpleName());
        return new ResponseEntity<>(error, HttpStatus.EXPECTATION_FAILED);
    }


    @ExceptionHandler(ExpiredJwtTokenException.class)
    public ResponseEntity<Object> expiredJwtExceptionHandler(ExpiredJwtTokenException ex, WebRequest request,
                                                             HttpServletResponse response) {
        log.error("!!!!!!!!! Exception Jwt Token Expired !!! : {}", ex.getMessage());
        List<String> details = new ArrayList<>();
        details.add(ex.getLocalizedMessage());
        var error = new ExceptionResponse(Constants.JWT_TOKEN_EXPIRED, details);
        error.setExcepion(ex.getClass().getSimpleName());
        return new ResponseEntity<>(error, HttpStatus.UNAUTHORIZED);
    }

}
