package com.tcashcroft.t65.exception;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class CustomExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(value = {NotFoundException.class})
    protected ResponseEntity<Object> handleNotFoundExceptions(NotFoundException ex, WebRequest request) {
        return handleExceptionInternal(ex, "Not found", new HttpHeaders(), HttpStatus.NOT_FOUND, request);
    }

    @ExceptionHandler(value = ForbiddenException.class)
    protected ResponseEntity<Object> handleForbiddenExceptions(ForbiddenException ex, WebRequest request) {
        return handleExceptionInternal(ex, "Forbidden", new HttpHeaders(), HttpStatus.FORBIDDEN, request);
    }

    @ExceptionHandler(value = {UpgradeNotAllowedException.class, ExistsException.class})
    protected ResponseEntity<Object> handleBadRequestExceptions(Exception ex, WebRequest request) {
        if (ex instanceof UpgradeNotAllowedException) {
            return handleExceptionInternal(ex, "Upgrade not allowed on this ship", new HttpHeaders(), HttpStatus.BAD_REQUEST, request);
        } else if (ex instanceof ExistsException) {
            return handleExceptionInternal(ex, "Already exists", new HttpHeaders(), HttpStatus.BAD_REQUEST, request);
        } else{
            return handleExceptionInternal(ex, "Bad Request", new HttpHeaders(), HttpStatus.BAD_REQUEST, request);
        }
    }
}
