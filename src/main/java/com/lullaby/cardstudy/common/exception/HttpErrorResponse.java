package com.lullaby.cardstudy.common.exception;

public record HttpErrorResponse(
        int code,
        String message
) {
}
