package com.example.demo.exceptions;

public class UserAlreadyExistsException extends RuntimeException{
    private static final long serialVersionUID = 5861310537366287163L;

    public UserAlreadyExistsException(final String message) {
        super(message);
    }
}
