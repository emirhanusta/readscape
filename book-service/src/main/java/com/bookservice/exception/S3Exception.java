package com.bookservice.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(reason = "S3 Exception")
public class S3Exception extends RuntimeException{
    HttpStatus status;
    public S3Exception(String message, HttpStatus status) {
        super(message);
        this.status = status;
    }
}
