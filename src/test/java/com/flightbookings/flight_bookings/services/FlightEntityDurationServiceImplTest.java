package com.flightbookings.flight_bookings.services;

import com.flightbookings.flight_bookings.models.FlightEntity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.Duration;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;
public class FlightEntityDurationServiceImplTest {
    private FlightDurationServiceImpl flightDurationService;

    @BeforeEach
    void setUp() {
        flightDurationService = new FlightDurationServiceImpl();
    }

    @Test
    void testCalculateFlightDuration_ValidTimes() {
        FlightEntity flight = new FlightEntity();
        flight.setDepartureTime(LocalDateTime.of(2024, 10, 12, 14, 0));
        flight.setArrivalTime(LocalDateTime.of(2024, 10, 12, 16, 0));

        Duration duration = flightDurationService.calculateFlightDuration(flight);

        assertNotNull(duration);
        assertEquals(Duration.ofHours(2), duration);
    }

    @Test
    void testCalculateFlightDuration_NullDepartureTime() {
        FlightEntity flight = new FlightEntity();
        flight.setDepartureTime(null);
        flight.setArrivalTime(LocalDateTime.of(2024, 10, 12, 16, 0));

        Duration duration = flightDurationService.calculateFlightDuration(flight);

        assertNull(duration);
    }

    @Test
    void testCalculateFlightDuration_NullArrivalTime() {
        FlightEntity flight = new FlightEntity();
        flight.setDepartureTime(LocalDateTime.of(2024, 10, 12, 14, 0));
        flight.setArrivalTime(null);

        Duration duration = flightDurationService.calculateFlightDuration(flight);

        assertNull(duration);
    }

    @Test
    void testCalculateFlightDuration_BothTimesNull() {
        FlightEntity flight = new FlightEntity();
        flight.setDepartureTime(null);
        flight.setArrivalTime(null);

        Duration duration = flightDurationService.calculateFlightDuration(flight);

        assertNull(duration);
    }
}
