package com.flightbookings.flight_bookings.exceptions;

public class NoSeatAssignedException extends RuntimeException {

    public NoSeatAssignedException(String message) {
        super(message);
    }

    public NoSeatAssignedException(String message, Throwable cause) {
        super(message, cause);
    }
}
