package com.targetcoders.sowing.authentication.exception;

public class InvalidSessionIdException extends RuntimeException {
    public InvalidSessionIdException(String message) {
        super(message);
    }
}
