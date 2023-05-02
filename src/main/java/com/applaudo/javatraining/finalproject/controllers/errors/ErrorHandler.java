package com.applaudo.javatraining.finalproject.controllers.errors;

import com.applaudo.javatraining.finalproject.controllers.errors.custom.BadRequestResponseStatusException;
import jakarta.persistence.EntityNotFoundException;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.server.ResponseStatusException;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class ErrorHandler {

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Map<String, String> handleInvalidArgument(MethodArgumentNotValidException ex) {
        Map<String, String> errorMap = new HashMap<>();
        ex.getBindingResult().getFieldErrors().forEach(error -> {
            errorMap.put(error.getField(), error.getDefaultMessage());
        });
        return errorMap;
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(ConstraintViolationException.class)
    public Map<String, String> handleConstraintValidation(ConstraintViolationException ex) {
        Map<String, String> errorMap = new HashMap<>();
        errorMap.put("error message", ex.getMessage());
        return errorMap;
    }

    @ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
    @ExceptionHandler(ResponseStatusException.class)
    public Map<String, String> handleUnprocessableEntity(ResponseStatusException ex) {
        Map<String, String> erroMap = new HashMap<>();
        erroMap.put("error message", ex.getReason());
        return erroMap;
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(EntityNotFoundException.class)
    public Map<String, String> handleItemNotFound(EntityNotFoundException ex) {
        Map<String, String> erroMap = new HashMap<>();
        erroMap.put("error message", ex.getMessage());
        return erroMap;
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler({HttpMessageNotReadableException.class})
    public Map<String, String> handleMessageNotReadable(HttpMessageNotReadableException ex) {
        Map<String, String> erroMap = new HashMap<>();
        erroMap.put("error message", ex.getMessage());
        return erroMap;
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler({BadRequestResponseStatusException.class})
    public Map<String, String> handleMessageBadRequest(BadRequestResponseStatusException ex) {
        Map<String, String> erroMap = new HashMap<>();
        erroMap.put("error message", ex.getMessage());
        return erroMap;
    }

}
