package com.applaudo.javatraining.finalproject.controllers.errors.custom;

public class BadRequestResponseStatusException extends RuntimeException{
    public BadRequestResponseStatusException(String message) {
        super(message);
    }
}