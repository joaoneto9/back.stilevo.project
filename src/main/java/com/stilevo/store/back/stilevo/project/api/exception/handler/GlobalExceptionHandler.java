package com.stilevo.store.back.stilevo.project.api.exception.handler;

import com.stilevo.store.back.stilevo.project.api.exception.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<ErrorResponse> handleNotFound(NotFoundException exception) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ErrorResponse(HttpStatus.NOT_FOUND.value(), exception.getMessage()));
    }

    @ExceptionHandler(ConflictException.class)
    public ResponseEntity<ErrorResponse> handleConflict(ConflictException exception) {
        return ResponseEntity.status(HttpStatus.CONFLICT).body(new ErrorResponse(HttpStatus.CONTINUE.value(), exception.getMessage()));
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleValidationErrorCep(MethodArgumentNotValidException exception) {
//        String mensagem = exception.getBindingResult()
//                .getFieldErrors()
//                .stream()
//                .map(erro -> erro.getDefaultMessage())
//                .findFirst()
//                .orElse("Dados inválidos");

        return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(new ErrorResponse(HttpStatus.UNPROCESSABLE_ENTITY.value(), exception.getMessage()));
    }

    @ExceptionHandler(InvallidCepException.class)
    public ResponseEntity<ErrorResponse> handleInvalidCep(InvallidCepException exception) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrorResponse(HttpStatus.BAD_REQUEST.value(), exception.getMessage()));
    }

    @ExceptionHandler(InvalidFormatCepException.class)
    public ResponseEntity<ErrorResponse> handleInvalidFormatCep(InvalidFormatCepException exception) {
        return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(new ErrorResponse(HttpStatus.UNPROCESSABLE_ENTITY.value(), exception.getMessage()));
    }

    @ExceptionHandler(InvalidAuthenticationUserException.class)
    public ResponseEntity<ErrorResponse> handleInvalidAuthenticationUser(InvalidAuthenticationUserException exception) {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new ErrorResponse(HttpStatus.UNAUTHORIZED.value(), exception.getMessage()));
    }

    @ExceptionHandler(InvalidPasswordException.class)
    public ResponseEntity<ErrorResponse> handleInvalidPasswordUser(InvalidPasswordException exception) {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new ErrorResponse(HttpStatus.UNAUTHORIZED.value(), exception.getMessage()));
    }

}
