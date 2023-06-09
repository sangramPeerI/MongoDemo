package com.mongo.exception;

public class DateNotValidException extends RuntimeException{
    public DateNotValidException() {
        super();
    }
    public DateNotValidException(String message) {
        super(message);
    }
}
