package com.stilevo.store.back.stilevo.project.api.controller.exception;

public class InvalidAuthenticationUserException extends RuntimeException {
    public InvalidAuthenticationUserException(String message) {
        super(message);
    }
}
