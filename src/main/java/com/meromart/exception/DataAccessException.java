package com.meromart.exception;

/**
 * Thrown when a database / DAO operation fails unexpectedly.
 */
public class DataAccessException extends RuntimeException {

    public DataAccessException(String message) {
        super(message);
    }

    public DataAccessException(String message, Throwable cause) {
        super(message, cause);
    }
}
