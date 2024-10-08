package com.flightbookings.flight_bookings.exceptions;

/**
 * Exception thrown when an unauthorized access attempt is made.
 */
public class UnauthorizedAccessException extends RuntimeException {
    /**
     * Constructs a new UnauthorizedAccessException with the specified detail message.
     *
     * @param message the detail message.
     */
    public UnauthorizedAccessException(String message) {
        super(message);
    }
}
