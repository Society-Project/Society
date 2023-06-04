package com.society.server.exception.userRelationshipsExceptions;

import com.society.server.exception.ApiException;
import org.springframework.http.HttpStatus;

public class RelationshipAlreadyExistsException extends ApiException {
    public RelationshipAlreadyExistsException(HttpStatus status, String message) {
        super(status, message);
    }
}
