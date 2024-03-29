package com.lullaby.cardstudy.common.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.client.HttpClientErrorException;

@Slf4j
@RestControllerAdvice
public class CommonExceptionHandler {

    @ExceptionHandler(MissingServletRequestParameterException.class)
    public ResponseEntity<HttpErrorResponse> handleMissingServletRequestParameterException(MissingServletRequestParameterException e) {
        log.error(e.getMessage(), e);
        return ResponseEntity.status(400)
                .body(new HttpErrorResponse(400, e.getMessage()));
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<HttpErrorResponse> handleIllegalArgumentException(IllegalArgumentException e) {
        log.error(e.getMessage(), e);
        return ResponseEntity.status(400)
                .body(new HttpErrorResponse(400, e.getMessage()));
    }

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<HttpErrorResponse> handleNotFoundException(NotFoundException e) {
        log.error(e.getMessage(), e);
        return ResponseEntity.status(404)
                .body(new HttpErrorResponse(404, e.getMessage()));
    }

    @ExceptionHandler(HttpClientErrorException.class)
    public ResponseEntity<HttpErrorResponse> handleHttpClientErrorException(HttpClientErrorException e) {
        log.error(e.getMessage(), e);
        return ResponseEntity.status(e.getStatusCode())
                .body(new HttpErrorResponse(e.getStatusCode().value(), e.getMessage()));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<HttpErrorResponse> handleException(Exception e) {
        log.error(e.getMessage(), e);
        return ResponseEntity.status(500)
                .body(new HttpErrorResponse(500, e.getMessage()));
    }

}
