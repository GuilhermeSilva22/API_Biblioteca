package com.backend.biblioteca.handler;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.backend.biblioteca.model.error.ErrorMessage;
import com.backend.biblioteca.model.exception.ResourceNotFoundException;

@ControllerAdvice
public class RestExceptionHandler {
    
    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<?> handleResourceNotFoundException(ResourceNotFoundException ex){
        ErrorMessage erro = new ErrorMessage("Not found", HttpStatus.NOT_FOUND.value(), ex.getMessage() );
        return new ResponseEntity<>(erro, HttpStatus.NOT_FOUND);

    }
}
