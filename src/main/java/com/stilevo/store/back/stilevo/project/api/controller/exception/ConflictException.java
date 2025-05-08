package com.stilevo.store.back.stilevo.project.api.controller.exception;

public class ConflictException extends RuntimeException {
    public ConflictException(String message) {
        super(message);
    }
}
