package com.flightbookings.flight_bookings.services;

import com.flightbookings.flight_bookings.models.Flight;
import com.flightbookings.flight_bookings.services.interfaces.FlightDurationService;
import org.springframework.stereotype.Service;

import java.time.Duration;

@Service
public class FlightDurationServiceImpl implements FlightDurationService {
    @Override
    public Duration calculateFlightDuration(Flight flight) {
        if (flight.getDepartureTime() != null && flight.getArrivalTime() != null) {
            return Duration.between(flight.getDepartureTime(), flight.getArrivalTime());
        }
        return null; // O establece un valor predeterminado
    }
}
