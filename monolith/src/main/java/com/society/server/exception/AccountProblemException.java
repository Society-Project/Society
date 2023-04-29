package com.society.server.exception;

import org.springframework.http.HttpStatus;

public class AccountProblemException extends ApiException{

    public AccountProblemException(HttpStatus status, String message) {
        super(status, message);
    }
}
