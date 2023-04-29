package com.society.server.exception;

import org.springframework.http.HttpStatus;

public class InvalidCredentialsException extends RuntimeException {
    private HttpStatus status;

    public InvalidCredentialsException(String message, HttpStatus status) {
        super(message);
        this.status = status;
    }

    public HttpStatus getStatus() {
        return status;
    }
}
