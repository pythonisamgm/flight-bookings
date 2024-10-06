//package com.flightbookings.flight_bookings.services;
//
//import com.flightbookings.flight_bookings.models.Flight;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//
//import java.time.Duration;
//import java.time.LocalDateTime;
//
//import static org.junit.jupiter.api.Assertions.*;
//public class FlightDurationServiceImplTest {
//    private FlightDurationServiceImpl flightDurationService;
//
//    @BeforeEach
//    void setUp() {
//        flightDurationService = new FlightDurationServiceImpl();
//    }
//
//    @Test
//    void testCalculateFlightDuration_ValidTimes() {
//        Flight flight = new Flight();
//        flight.setDepartureTime(LocalDateTime.of(2024, 10, 12, 14, 0));
//        flight.setArrivalTime(LocalDateTime.of(2024, 10, 12, 16, 0));
//
//        Duration duration = flightDurationService.calculateFlightDuration(flight);
//
//        assertNotNull(duration);
//        assertEquals(Duration.ofHours(2), duration);
//    }
//
//    @Test
//    void testCalculateFlightDuration_NullDepartureTime() {
//        Flight flight = new Flight();
//        flight.setDepartureTime(null);
//        flight.setArrivalTime(LocalDateTime.of(2024, 10, 12, 16, 0));
//
//        Duration duration = flightDurationService.calculateFlightDuration(flight);
//
//        assertNull(duration);
//    }
//
//    @Test
//    void testCalculateFlightDuration_NullArrivalTime() {
//        Flight flight = new Flight();
//        flight.setDepartureTime(LocalDateTime.of(2024, 10, 12, 14, 0));
//        flight.setArrivalTime(null);
//
//        Duration duration = flightDurationService.calculateFlightDuration(flight);
//
//        assertNull(duration);
//    }
//
//    @Test
//    void testCalculateFlightDuration_BothTimesNull() {
//        Flight flight = new Flight();
//        flight.setDepartureTime(null);
//        flight.setArrivalTime(null);
//
//        Duration duration = flightDurationService.calculateFlightDuration(flight);
//
//        assertNull(duration);
//    }
//}
