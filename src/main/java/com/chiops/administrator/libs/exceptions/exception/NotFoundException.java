package com.chiops.administrator.libs.exceptions.exception;


public class NotFoundException extends RuntimeException {
    public NotFoundException(String message) {
        super(message);
    }
}