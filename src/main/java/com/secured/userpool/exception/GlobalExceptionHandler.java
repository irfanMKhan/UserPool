package com.secured.userpool.exception;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ResponseBody
    @ExceptionHandler(Exception.class)
    public ResponseEntity<?> handleException(Exception exception, HttpServletRequest request, HttpServletResponse response) {
        return new ResponseEntity<>(exception.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ResponseBody
    @ExceptionHandler(CommonException.class)
    public ResponseEntity<?> handleCommonException(CommonException exception, HttpServletRequest request, HttpServletResponse response) {
        return new ResponseEntity<>(exception.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ResponseBody
    @ExceptionHandler(CommonResponseException.class)
    public ResponseEntity<?> handleCommonResponseException(CommonResponseException exception, HttpServletRequest request, HttpServletResponse response) {
        return new ResponseEntity<>(exception.getMessage(), HttpStatusCode.valueOf(response.getStatus()));
    }
}
