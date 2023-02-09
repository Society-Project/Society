package com.beval.server.exception;

import org.springframework.http.HttpStatus;

public class UserBannedException extends ApiException{
    public UserBannedException(HttpStatus status, String message) {
        super(status, message);
    }

    public UserBannedException() {
        super(HttpStatus.FORBIDDEN, "User is banned!");
    }
}
