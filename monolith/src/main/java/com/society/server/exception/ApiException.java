package com.society.server.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
//General Api Exception
public class ApiException extends RuntimeException{
    private final HttpStatus status;

    public ApiException(HttpStatus status, String message) {
        super(message);
        this.status = status;
    }

}
