package com.flightbookings.flight_bookings.exceptions;

/**
 * Exception thrown when a seat cannot be found.
 */
public class SeatNotFoundException extends RuntimeException {
    /**
     * Constructs a new SeatNotFoundException with the specified detail message.
     *
     * @param message the detail message.
     */
    public SeatNotFoundException(String message) {
        super(message);
    }

    /**
     * Constructs a new SeatNotFoundException with the specified detail message and cause.
     *
     * @param message the detail message.
     * @param cause   the cause of the exception.
     */
    public SeatNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}