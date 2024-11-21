package com.flightbookings.flight_bookings.services.interfaces;

import com.flightbookings.flight_bookings.models.FlightEntity;

import java.time.Duration;
/**
 * Interface for calculating the duration of a flight in the flight booking system.
 */
public interface FlightDurationService {
    /**
     * Calculates the duration of a specified flight.
     *
     * @param flight the Flight object for which the duration is to be calculated.
     * @return the duration of the flight as a Duration object.
     */
    Duration calculateFlightDuration(FlightEntity flight);
}
