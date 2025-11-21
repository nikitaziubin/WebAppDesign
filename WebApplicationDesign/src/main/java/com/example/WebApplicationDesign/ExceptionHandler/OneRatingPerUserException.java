package com.example.WebApplicationDesign.ExceptionHandler;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class OneRatingPerUserException extends RuntimeException {
    public OneRatingPerUserException(String message) {
        super(message);
    }
}
