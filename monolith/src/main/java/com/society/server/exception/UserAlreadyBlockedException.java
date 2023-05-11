package com.society.server.exception;

import org.springframework.http.HttpStatus;

public class UserAlreadyBlockedException extends ApiException {

    public UserAlreadyBlockedException(HttpStatus status, String message) {
        super(status, message);
    }
}
