package com.flightbookings.flight_bookings.services;

import com.flightbookings.flight_bookings.models.Flight;
import com.flightbookings.flight_bookings.services.interfaces.FlightDurationService;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDateTime;

@Service
public class FlightDurationServiceImpl implements FlightDurationService {
    @Override
    public int calculateFlightDuration(Flight flight) {
        LocalDateTime departureTime = flight.getDepartureTime();
        LocalDateTime arrivalTime = flight.getArrivalTime();

        if (departureTime != null && arrivalTime != null) {
            Duration duration = Duration.between(departureTime, arrivalTime);
            return (int) duration.toMinutes();
        }

        return 0;
    }
}
