package com.flightbookings.flight_bookings.services;

import com.flightbookings.flight_bookings.exceptions.MissingFlightTimeException;
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
    void testCalculateFlightDuration_ValidTimes() {
        Flight flight = new Flight();
        flight.setDepartureTime(LocalDateTime.of(2024, 10, 12, 14, 0));
        flight.setArrivalTime(LocalDateTime.of(2024, 10, 12, 16, 30)); // 2 horas y 30 minutos

        int durationInMinutes = flightDurationService.calculateFlightDuration(flight);

        assertEquals(150, durationInMinutes); // 2 horas y 30 minutos son 150 minutos
    }
    @Test
    void testCalculateFlightDuration_NullDepartureOrArrivalTime() {
        Flight flight = new Flight();
        flight.setDepartureTime(null); // O setArrivalTime(null);

        assertThrows(MissingFlightTimeException.class, () -> {
            flightDurationService.calculateFlightDuration(flight);
        });
    }

    @Test
    void testCalculateFlightDuration_NullArrivalTime() {
        Flight flight = new Flight();
        flight.setDepartureTime(LocalDateTime.of(2024, 10, 12, 14, 0));
        flight.setArrivalTime(null);

        assertThrows(MissingFlightTimeException.class, () -> {
            flightDurationService.calculateFlightDuration(flight);
        });
    }

    @Test
    void testCalculateFlightDuration_BothTimesNull() {
        Flight flight = new Flight();
        flight.setDepartureTime(null);
        flight.setArrivalTime(null);

        assertThrows(MissingFlightTimeException.class, () -> {
            flightDurationService.calculateFlightDuration(flight);
        });
    }
}
