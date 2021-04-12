package com.askrindo.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class OldPasswordNotMatchesException extends RuntimeException{
    public OldPasswordNotMatchesException(String message){
        super(message);
    }
    public OldPasswordNotMatchesException(String message, Throwable cause){
        super(message, cause);
    }
}
