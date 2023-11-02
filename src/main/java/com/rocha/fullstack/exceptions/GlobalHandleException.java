package com.rocha.fullstack.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalHandleException {

    @ExceptionHandler(EmailNotAvailableException.class)
    public ResponseEntity<Map<String, String>> handleEmailNotAvailableException(EmailNotAvailableException ex) {

        Map<String, String> resp = new HashMap<>();

        resp.put("ERROR: ", ex.getMessage());

        return new ResponseEntity<>(resp, HttpStatus.CONFLICT);
    }

    @ExceptionHandler(MainProductNotFoundException.class)
    public ResponseEntity<Map<String, String>> handleMainProductNotFoundException(MainProductNotFoundException ex) {

        Map<String, String> resp = new HashMap<>();

        resp.put("ERROR: ", ex.getMessage());

        return new ResponseEntity<>(resp, HttpStatus.CONFLICT);
    }
}
