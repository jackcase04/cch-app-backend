package com.cch.cch_app.exception;

public class NameAlreadyRegisteredException extends RuntimeException {
    public NameAlreadyRegisteredException(String message) {
        super(message);
    }
}
