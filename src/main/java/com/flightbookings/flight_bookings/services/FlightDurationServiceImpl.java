package com.flightbookings.flight_bookings.services;

import com.flightbookings.flight_bookings.models.Flight;
import com.flightbookings.flight_bookings.services.interfaces.FlightDurationService;
import org.springframework.stereotype.Service;

import java.time.Duration;
/**
 * Service implementation for calculating the duration of a flight.
 * This service calculates the time difference between a flight's departure and arrival times.
 */
@Service
public class FlightDurationServiceImpl implements FlightDurationService {
    /**
     * Calculates the duration of a given flight based on its departure and arrival times.
     *
     * @param flight the Flight object containing departure and arrival times.
     * @return a Duration object representing the time difference between departure and arrival,
     *         or null if either the departure or arrival time is not set.
     */
    @Override
    public Duration calculateFlightDuration(Flight flight) {
        if (flight.getDepartureTime() != null && flight.getArrivalTime() != null) {
            return Duration.between(flight.getDepartureTime(), flight.getArrivalTime());
        }
        return null;
    }
}
