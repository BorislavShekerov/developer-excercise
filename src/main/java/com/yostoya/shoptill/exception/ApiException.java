package com.yostoya.shoptill.exception;

public class ApiException extends RuntimeException {
    public ApiException(final String message) {
        super(message);
    }
}
