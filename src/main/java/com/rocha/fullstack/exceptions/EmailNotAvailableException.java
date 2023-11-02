package com.rocha.fullstack.exceptions;

public class EmailNotAvailableException extends RuntimeException {
    public EmailNotAvailableException(String message) {
        super(message);
    }
}
