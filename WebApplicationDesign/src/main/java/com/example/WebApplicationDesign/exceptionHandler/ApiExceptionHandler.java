package com.example.WebApplicationDesign.exceptionHandler;

import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Map;
import java.util.Objects;

@RestControllerAdvice
public class ApiExceptionHandler {

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<Map<String,Object>> badJson(HttpMessageNotReadableException ex) {
        ex.getMostSpecificCause();
        Throwable root = ex.getMostSpecificCause();
        return ResponseEntity.badRequest().body(Map.of(
                "status", 400,
                "error", "Wrong JSON Format",
                "message", root.getMessage()
        ));
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String,Object>> validation(MethodArgumentNotValidException ex) {
        var errors = ex.getBindingResult().getFieldErrors().stream()
                .map(fe -> Map.of("field", fe.getField(), "message", Objects.requireNonNull(fe.getDefaultMessage())))
                .toList();
        return ResponseEntity.badRequest().body(Map.of("status", 400, "errors", errors));
    }
}
