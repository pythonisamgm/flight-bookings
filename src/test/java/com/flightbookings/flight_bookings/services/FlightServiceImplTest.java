package com.flightbookings.flight_bookings.services;

import com.flightbookings.flight_bookings.models.Flight;
import com.flightbookings.flight_bookings.models.EFlightAirplane;
import com.flightbookings.flight_bookings.repositories.IFlightRepository;
import com.flightbookings.flight_bookings.repositories.ISeatRepository;
import com.flightbookings.flight_bookings.services.interfaces.FlightDurationService;
import com.flightbookings.flight_bookings.services.interfaces.SeatService;
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
    private SeatService seatService;

    @Mock
    private ISeatRepository seatRepository;

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
        when(flightRepository.save(any(Flight.class))).thenAnswer(invocation -> invocation.getArgument(0));
        when(seatService.initializeSeats(any(Flight.class), anyInt())).thenReturn(List.of("1A", "1B", "1C"));
        when(seatRepository.findByFlight(any(Flight.class))).thenReturn(new ArrayList<>());

        Flight createdFlight = flightService.createFlight(flight);

        assertNotNull(createdFlight, "The created flight should not be null");
        verify(flightRepository, times(1)).save(flight);
        verify(seatService, times(1)).initializeSeats(createdFlight, flight.getNumRows());
        verify(seatRepository, times(1)).findByFlight(createdFlight);
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
        Flight updatedFlight = new Flight();
        updatedFlight.setFlightNumber(124);
        updatedFlight.setDepartureTime(LocalDateTime.of(2024, 10, 12, 15, 0));
        updatedFlight.setArrivalTime(LocalDateTime.of(2024, 10, 12, 17, 0));
        updatedFlight.setFlightAirplane(EFlightAirplane.BOEING_777);
        updatedFlight.setCapacityPlane(250);
        updatedFlight.setAvailability(false);
        updatedFlight.setNumRows(25);
        updatedFlight.setFlightPrice(BigDecimal.valueOf(350));

        // Configuración del mock
        when(flightRepository.existsById(1L)).thenReturn(true);
        when(flightRepository.save(any(Flight.class))).thenReturn(updatedFlight);

        // Ejecuta el método
        Flight result = flightService.updateFlight(1L, updatedFlight);

        // Verificaciones
        assertNotNull(result, "The updated flight should not be null");
        assertEquals(124, result.getFlightNumber(), "The flight number does not match");
        verify(flightRepository, times(1)).existsById(1L);
        verify(flightRepository, times(1)).save(updatedFlight);
    }

    @Test
    void testDeleteFlight() {
        when(flightRepository.existsById(1L)).thenReturn(true);

        boolean result = flightService.deleteFlight(1L);

        assertTrue(result, "The flight deletion should return true");
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

}
