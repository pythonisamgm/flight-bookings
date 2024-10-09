package com.flightbookings.flight_bookings.services;

import com.flightbookings.flight_bookings.models.Flight;
import com.flightbookings.flight_bookings.services.interfaces.FlightDurationService;
import org.springframework.stereotype.Service;

import java.time.Duration;

@Service
public class FlightDurationServiceImpl implements FlightDurationService {
    @Override
    public Long calculateFlightDuration(Flight flight) {
        if (flight.getDepartureTime() != null && flight.getArrivalTime() != null) {
            Duration duration = Duration.between(flight.getDepartureTime(), flight.getArrivalTime());
            long minutes = duration.toMinutes(); // Convertimos la duración a minutos.

            flight.setFlightDuration(minutes); // Guardamos la duración en minutos.
            return minutes;
        }
        return null;
    }
}
