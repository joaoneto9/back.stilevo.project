package com.stilevo.store.back.stilevo.project.api.exception;

public class InvalidAuthenticationUserException extends RuntimeException {
    public InvalidAuthenticationUserException(String message) {
        super(message);
    }
}
