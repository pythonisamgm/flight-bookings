package com.flightbookings.flight_bookings.services;

import com.flightbookings.flight_bookings.models.Flight;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;
public class FlightDurationServiceImplTest {
    private FlightDurationServiceImpl flightDurationService;

    @BeforeEach
    void setUp() {
        flightDurationService = new FlightDurationServiceImpl();
    }

    @Test
    void testCalculateFlightDuration_NullDepartureTime() {
        Flight flight = new Flight();
        flight.setDepartureTime(null);
        flight.setArrivalTime(LocalDateTime.of(2024, 10, 12, 16, 0));

        Integer durationInMinutes = flightDurationService.calculateFlightDuration(flight);

        assertNull(durationInMinutes);
    }

    @Test
    void testCalculateFlightDuration_NullArrivalTime() {
        Flight flight = new Flight();
        flight.setDepartureTime(LocalDateTime.of(2024, 10, 12, 14, 0));
        flight.setArrivalTime(null);

        Integer durationInMinutes = flightDurationService.calculateFlightDuration(flight);

        assertNull(durationInMinutes);
    }

    @Test
    void testCalculateFlightDuration_BothTimesNull() {
        Flight flight = new Flight();
        flight.setDepartureTime(null);
        flight.setArrivalTime(null);

        Integer durationInMinutes = flightDurationService.calculateFlightDuration(flight);

        assertNull(durationInMinutes);
    }
}
