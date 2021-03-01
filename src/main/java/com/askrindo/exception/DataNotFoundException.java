package com.askrindo.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class DataNotFoundException extends RuntimeException {

    public static final String DATA_NOT_FOUND = "Resource %s with ID: %s Not Found!";

    public DataNotFoundException(String message) {
        super(message);
    }
}
