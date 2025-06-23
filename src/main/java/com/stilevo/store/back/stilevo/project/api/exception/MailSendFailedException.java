package com.stilevo.store.back.stilevo.project.api.exception;

public class MailSendFailedException extends RuntimeException {
    public MailSendFailedException(String message) {
        super(message);
    }
}
