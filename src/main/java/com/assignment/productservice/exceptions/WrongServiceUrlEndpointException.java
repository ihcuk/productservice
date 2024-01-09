package com.assignment.productservice.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
public class WrongServiceUrlEndpointException extends RuntimeException {

    public WrongServiceUrlEndpointException(String message) {
        super(message);
    }
}
