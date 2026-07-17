package org.generation.italy.model.exceptions;

public class BadRequestException extends RuntimeException {
    private final String errorCode;

    public BadRequestException(String errorCode, String message) {
        super(message);
        this.errorCode = errorCode;
    }

    public String getErrorCode() {
        return errorCode;
    }
}
