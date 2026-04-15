package com.meromart.exception;

/**
 * Thrown when an authentication operation fails (e.g., bad credentials, expired session).
 */
public class AuthenticationException extends RuntimeException {

    public AuthenticationException(String message) {
        super(message);
    }

    public AuthenticationException(String message, Throwable cause) {
        super(message, cause);
    }
}
