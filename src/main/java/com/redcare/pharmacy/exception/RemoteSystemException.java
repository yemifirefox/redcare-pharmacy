package com.redcare.pharmacy.exception;

import org.springframework.http.HttpStatus;

public class RemoteSystemException extends RuntimeException{

    private final HttpStatus status;
    public RemoteSystemException(HttpStatus status, String message) {
        super(message);
        this.status = status;
    }

    public HttpStatus getStatus() {
        return status;
    }
}
