package com.flightbookings.flight_bookings.exceptions;

/**
 * Exception thrown when a passenger cannot be found.
 */
public class PassengerNotFoundException extends RuntimeException {
    /**
     * Constructs a new PassengerNotFoundException with the specified detail message.
     *
     * @param message the detail message.
     */
    public PassengerNotFoundException(String message) {
        super(message);
    }

    /**
     * Constructs a new PassengerNotFoundException with the specified detail message and cause.
     *
     * @param message the detail message.
     * @param cause   the cause of the exception.
     */
    public PassengerNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}