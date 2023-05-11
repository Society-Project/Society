package com.society.server.exception.handler;

import com.society.server.dto.response.ResponseDTO;
import com.society.server.exception.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.*;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;

@RestControllerAdvice
public class GlobalExceptionHandler {

    //exceptions for user authentications are later caught by the authenticationEntryPoint if not here
    //but this is required because of swagger
    @ExceptionHandler({DisabledException.class, BadCredentialsException.class,
            LockedException.class, CredentialsExpiredException.class, AccountExpiredException.class})
    public ResponseEntity<Object> handleAccountExceptions(Exception ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                ResponseDTO
                        .builder()
                        .message(ex.getMessage())
                        .status(HttpStatus.BAD_REQUEST.value())
                        .content(null)
                        .timestamp(LocalDateTime.now())
                        .build()
        );
    }

    @ExceptionHandler({ApiException.class})
    public ResponseEntity<Object> handleCustomExceptions(ApiException ex) {
        return ResponseEntity.status(ex.getStatus())
                .body(
                        ResponseDTO
                                .builder()
                                .message(ex.getMessage())
                                .status(ex.getStatus().value())
                                .content(null)
                                .build()
                );
    }

    //@PreAuthorize handler
    @ExceptionHandler({AccessDeniedException.class})
    public ResponseEntity<Object> handleAccessDenied(Exception ex) {
        return ResponseEntity.status(HttpStatus.FORBIDDEN)
                .body(
                        ResponseDTO
                                .builder()
                                .status(HttpStatus.FORBIDDEN.value())
                                .message(ex.getMessage())
                                .content(null)
                                .build()
                );
    }

}
