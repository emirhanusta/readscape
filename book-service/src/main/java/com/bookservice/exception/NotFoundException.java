package com.bookservice.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.NOT_FOUND)
public class NotFoundException extends RuntimeException{

    private final ExceptionMessage message;
    public NotFoundException(ExceptionMessage message) {
        this.message = message;
    }

    public ExceptionMessage getExceptionMessage() {
        return message;
    }
}
