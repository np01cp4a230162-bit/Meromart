package com.meromart.exception;

/**
 * Thrown when user input fails business-rule validation (e.g., passwords don't match).
 */
public class ValidationException extends RuntimeException {

    public ValidationException(String message) {
        super(message);
    }

    public ValidationException(String message, Throwable cause) {
        super(message, cause);
    }
}
