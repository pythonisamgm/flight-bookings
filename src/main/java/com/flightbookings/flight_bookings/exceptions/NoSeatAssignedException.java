package com.flightbookings.flight_bookings.exceptions;

/**
 * Exception thrown when no seat is assigned for a particular booking or flight.
 */

public class NoSeatAssignedException extends RuntimeException {
    /**
     * Constructs a new NoSeatAssignedException with the specified detail message.
     *
     * @param message the detail message.
     */
    public NoSeatAssignedException(String message) {
        super(message);
    }

    /**
     * Constructs a new NoSeatAssignedException with the specified detail message and cause.
     *
     * @param message the detail message.
     * @param cause   the cause of the exception.
     */
    public NoSeatAssignedException(String message, Throwable cause) {
        super(message, cause);
    }
}
