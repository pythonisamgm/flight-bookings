package com.flightbookings.flight_bookings.services.interfaces;

import com.flightbookings.flight_bookings.models.Flight;

import java.time.Duration;

public interface FlightDurationService {
    Duration calculateFlightDuration(Flight flight);
}
