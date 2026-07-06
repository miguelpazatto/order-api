package com.miguelpazatto.orderapi.controllers.exceptions;

import com.miguelpazatto.orderapi.services.exceptions.BusinessException;
import com.miguelpazatto.orderapi.services.exceptions.ResourceNotFoundException;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.Instant;

@RestControllerAdvice
public class ResourceExceptionHandler {

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<StandardError> resourceNotFound(ResourceNotFoundException e, HttpServletRequest request) {
        StandardError error = new StandardError(Instant.now(), HttpStatus.NOT_FOUND.value(), "Resource not found", e.getMessage(), request.getRequestURI());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
    }

    @ExceptionHandler(BusinessException.class)
    public ResponseEntity<StandardError> businessError(BusinessException e, HttpServletRequest request) {
        StandardError error = new StandardError(Instant.now(), HttpStatus.BAD_REQUEST.value(), "Business rule violation", e.getMessage(),request.getRequestURI());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
    }
}
