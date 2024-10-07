package com.flightbookings.flight_bookings.services.interfaces;

import com.flightbookings.flight_bookings.models.Flight;

import java.time.Duration;

public interface FlightDurationService {
    int calculateFlightDuration(Flight flight);
}
