package com.rocha.fullstack.exceptions;

public class MainProductNotFoundException extends RuntimeException {
    public MainProductNotFoundException(String message) {
        super(message);
    }
}
