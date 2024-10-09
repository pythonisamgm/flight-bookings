package com.flightbookings.flight_bookings.services;

import com.flightbookings.flight_bookings.models.Flight;
import com.flightbookings.flight_bookings.models.EFlightAirplane;
import com.flightbookings.flight_bookings.models.Seat;
import com.flightbookings.flight_bookings.repositories.IFlightRepository;
import com.flightbookings.flight_bookings.services.interfaces.FlightDurationService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.math.BigDecimal;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class FlightServiceImplTest {

    @InjectMocks
    private FlightServiceImpl flightService;

    @Mock
    private IFlightRepository flightRepository;

    @Mock
    private FlightDurationService flightDurationService;

    private Flight flight;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        flight = new Flight();
        flight.setFlightId(1L);
        flight.setFlightNumber(123);
        flight.setDepartureTime(LocalDateTime.of(2024, 10, 12, 14, 0));
        flight.setArrivalTime(LocalDateTime.of(2024, 10, 12, 16, 0));
        flight.setFlightAirplane(EFlightAirplane.BOEING_747);
        flight.setCapacityPlane(200);
        flight.setAvailability(true);
        flight.setNumRows(20);
        flight.setFlightPrice(BigDecimal.valueOf(300));
        flight.setFlightDuration(Duration.ofHours(2));
    }

    @Test
    void testCreateFlight() {
        when(flightDurationService.calculateFlightDuration(any(Flight.class))).thenReturn(Duration.ofHours(2));
        when(flightRepository.save(any(Flight.class))).thenReturn(flight);

        Flight createdFlight = flightService.createFlight(flight);

        assertNotNull(createdFlight);
        assertEquals(flight.getFlightId(), createdFlight.getFlightId());
        assertEquals(flight.getFlightNumber(), createdFlight.getFlightNumber());
        verify(flightRepository).save(flight);
    }

    @Test
    void testGetFlightById() {
        when(flightRepository.findById(1L)).thenReturn(Optional.of(flight));

        Flight foundFlight = flightService.getFlightById(1L);

        assertNotNull(foundFlight);
        assertEquals(flight.getFlightId(), foundFlight.getFlightId());
        verify(flightRepository).findById(1L);
    }

    @Test
    void testGetAllFlights() {
        List<Flight> flights = new ArrayList<>();
        flights.add(flight);
        when(flightRepository.findAll()).thenReturn(flights);

        List<Flight> allFlights = flightService.getAllFlights();

        assertFalse(allFlights.isEmpty());
        assertEquals(1, allFlights.size());
        assertEquals(flight.getFlightId(), allFlights.get(0).getFlightId());
        verify(flightRepository).findAll();
    }

    @Test
    void testUpdateFlight() {
        when(flightRepository.save(any(Flight.class))).thenReturn(flight);

        Flight updatedFlight = flight;
        updatedFlight.setFlightNumber(124);
        updatedFlight.setDepartureTime(LocalDateTime.of(2024, 10, 12, 15, 0));
        updatedFlight.setArrivalTime(LocalDateTime.of(2024, 10, 12, 17, 0));
        updatedFlight.setFlightAirplane(EFlightAirplane.BOEING_777);
        updatedFlight.setCapacityPlane(250);
        updatedFlight.setAvailability(false);
        updatedFlight.setNumRows(25);
        updatedFlight.setFlightPrice(BigDecimal.valueOf(350));

        flightService.updateFlight(1L, updatedFlight);

        assertNotNull(updatedFlight);
        assertEquals(124, updatedFlight.getFlightNumber());
        assertEquals(LocalDateTime.of(2024, 10, 12, 15, 0), updatedFlight.getDepartureTime());
        assertEquals(LocalDateTime.of(2024, 10, 12, 17, 0), updatedFlight.getArrivalTime());
        assertEquals(EFlightAirplane.BOEING_777, updatedFlight.getFlightAirplane());
        assertEquals(250, updatedFlight.getCapacityPlane());
        assertFalse(updatedFlight.isAvailability());
        assertEquals(25, updatedFlight.getNumRows());
        assertEquals(BigDecimal.valueOf(350), updatedFlight.getFlightPrice());
    }

    @Test
    void testDeleteFlight() {
        when(flightRepository.existsById(1L)).thenReturn(true);
        when(flightRepository.findById(1L)).thenReturn(Optional.of(flight));

        boolean result = flightService.deleteFlight(1L);

        assertTrue(result);
        verify(flightRepository, times(1)).deleteById(1L);
    }

    @Test
    void testDelayFlight() {
        LocalDateTime newDepartureTime = LocalDateTime.of(2024, 10, 12, 15, 30);
        when(flightRepository.findById(1L)).thenReturn(Optional.of(flight));
        when(flightDurationService.calculateFlightDuration(any(Flight.class))).thenReturn(Duration.ofHours(2));

        flightService.delayFlight(1L, newDepartureTime);

        assertEquals(newDepartureTime, flight.getDepartureTime());
        verify(flightRepository).save(flight);
    }

    @Test
    void testUpdateFlightAvailability() {
        Flight flight1 = new Flight();
        flight1.setFlightId(1L);
        flight1.setDepartureTime(LocalDateTime.now().plusHours(1));
        flight1.setAvailability(true);

        Seat seat1 = new Seat();
        seat1.setBooked(true);
        Seat seat2 = new Seat();
        seat2.setBooked(false);

        flight1.setSeats(List.of(seat1, seat2));

        Flight flight2 = new Flight();
        flight2.setFlightId(2L);
        flight2.setDepartureTime(LocalDateTime.now().minusHours(1));
        flight2.setAvailability(true);

        flight2.setSeats(List.of(seat1, seat2));

        when(flightRepository.findAll()).thenReturn(List.of(flight1, flight2));

        flightService.updateFlightAvailability();

        assertTrue(flight1.isAvailability());
        assertFalse(flight2.isAvailability());

        verify(flightRepository).save(flight1);
        verify(flightRepository).save(flight2);
    }

}
