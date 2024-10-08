package com.flightbookings.flight_bookings.exceptions;

public class MissingFlightTimeException extends RuntimeException {
    public MissingFlightTimeException(String message) {
        super(message);
    }
}
