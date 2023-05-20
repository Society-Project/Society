package com.society.server.exception.userRelationshipsExceptions;

import com.society.server.exception.ApiException;
import org.springframework.http.HttpStatus;

public class RelationshipNotExistException extends ApiException {
    public RelationshipNotExistException(HttpStatus status, String message) {
        super(status, message);
    }
}
