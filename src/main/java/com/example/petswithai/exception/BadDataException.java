package com.example.petswithai.exception;

public class BadDataException extends RuntimeException {
    public BadDataException(String message) {
        super(message);
    }
}