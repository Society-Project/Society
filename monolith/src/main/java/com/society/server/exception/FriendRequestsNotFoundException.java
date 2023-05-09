package com.society.server.exception;

import org.springframework.http.HttpStatus;

public class FriendRequestsNotFoundException extends ApiException {

    public FriendRequestsNotFoundException() {
        super(HttpStatus.NOT_FOUND, "Friend requests not found!");
    }

    public FriendRequestsNotFoundException(HttpStatus status, String message) {
        super(status, message);
    }
}
