package com.blabbo.app.blabbo.exceptions;

public class InvalidJwtException extends RuntimeException {
    private final String message;


    public InvalidJwtException(String message) {
        this.message = message;
    }


    @Override
    public String getMessage() {
        return message;
    }
}
