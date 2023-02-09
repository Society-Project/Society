package com.beval.server.exception;

import org.springframework.http.HttpStatus;

public class ResourceNotFoundException extends ApiException{
    public ResourceNotFoundException() {
        super(HttpStatus.NOT_FOUND, "Resource not found");
    }

    public ResourceNotFoundException(HttpStatus status, String message) {
        super(status, message);
    }
}
