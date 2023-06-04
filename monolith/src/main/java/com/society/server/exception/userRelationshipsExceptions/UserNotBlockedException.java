package com.society.server.exception.userRelationshipsExceptions;


import com.society.server.exception.ApiException;
import org.springframework.http.HttpStatus;

public class UserNotBlockedException extends ApiException {

    public UserNotBlockedException(HttpStatus status, String message) {
        super(status, message);
    }
}
