package com.society.server.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class UserNotFoundException extends ApiException{

    public UserNotFoundException(String message) {
        super(HttpStatus.NOT_FOUND, message);
    }
}
