package com.society.server.exception;

import org.springframework.http.HttpStatus;

public class NotAuthorizedException extends ApiException{
    public NotAuthorizedException() {
        super(HttpStatus.UNAUTHORIZED, "You are not authorized to view this resource");
    }

    public NotAuthorizedException(HttpStatus status, String message) {
        super(status, message);
    }
}
