package com.company.empresa.exceptions;

import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.logging.Logger;

@Slf4j
@ControllerAdvice
@NoArgsConstructor
public class BaseException {


    @ExceptionHandler(value = {UserNameNotFoundException.class})
    public ResponseEntity<ErrorResponse> handleSpringException(UserNameNotFoundException userNameNotFoundException){
        log.error("Error causado por " + userNameNotFoundException);
        return new ResponseEntity<>(userNameNotFoundException.getErrorResponse(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(value = {RuntimeException.class})
    public ResponseEntity<?> handleSpringException(RuntimeException runtimeException){
        ErrorResponse errorResponse = new ErrorResponse("Error de servidor", "error");
        return new ResponseEntity<>(errorResponse, HttpStatus.CONFLICT);
    }
}
