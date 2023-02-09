package com.beval.server.exception;

import org.springframework.http.HttpStatus;

public class RoleNotFoundException extends ApiException{
    public RoleNotFoundException() {
        super(HttpStatus.INTERNAL_SERVER_ERROR, "Role not found!");
    }

    public RoleNotFoundException(HttpStatus status, String message) {
        super(status, message);
    }
}
