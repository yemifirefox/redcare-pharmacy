package com.redcare.pharmacy.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(RemoteSystemException.class)
    public ResponseEntity<ErrorDto> handleWebClientResponseException(RemoteSystemException ex) {
    return ResponseEntity.status(ex.getStatus()).body(new ErrorDto(ex.getMessage()));
    }
}