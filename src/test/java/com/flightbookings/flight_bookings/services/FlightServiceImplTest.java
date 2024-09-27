package com.flightbookings.flight_bookings.services;

import com.flightbookings.flight_bookings.models.EFlightAirplane;
import com.flightbookings.flight_bookings.models.Flight;
import com.flightbookings.flight_bookings.repositories.IFlightRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class FlightServiceImplTest {

    @Mock
    private IFlightRepository flightRepository;

    @InjectMocks
    private FlightServiceImpl flightService;

    private Flight flight1;
    private Flight flight2;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);

        flight1 = new Flight();
        flight1.setFlightId(1L);
        flight1.setFlightNumber(123);
        flight1.setDepartureTime(LocalDateTime.of(2024, 9, 30, 14, 0));
        flight1.setArrivalTime(LocalDateTime.of(2024, 9, 30, 17, 30));
        flight1.setFlightAirplane(EFlightAirplane.Boeing_737);
        flight1.setCapacityPlane(180);
        flight1.setAvailability(true);
        flight1.setNumRows(30);
        flight1.setFlightPrice(new BigDecimal("300.00"));
        flight1.setSeats(new ArrayList<>());
        flight1.setBookingList(new ArrayList<>());
        //flight1.setAirports(new HashSet<>());

        flight2 = new Flight();
        flight2.setFlightId(2L);
        flight2.setFlightNumber(456);
        flight2.setDepartureTime(LocalDateTime.of(2024, 10, 1, 9, 0));
        flight2.setArrivalTime(LocalDateTime.of(2024, 10, 1, 12, 0));
        flight2.setFlightAirplane(EFlightAirplane.Airbus_A321);
        flight2.setCapacityPlane(160);
        flight2.setAvailability(true);
        flight2.setNumRows(28);
        flight2.setFlightPrice(new BigDecimal("200.00"));
        flight2.setSeats(new ArrayList<>());
        flight2.setBookingList(new ArrayList<>());
        //flight2.setAirports(new HashSet<>());
    }

    @Test
    public void testCreateFlight() {
        when(flightRepository.save(any(Flight.class))).thenReturn(flight1);

        Flight createdFlight = flightService.createFlight(flight1);

        assertNotNull(createdFlight);
        assertEquals(1L, createdFlight.getFlightId());
        assertEquals(123, createdFlight.getFlightNumber());
        assertEquals(LocalDateTime.of(2024, 9, 30, 14, 0), createdFlight.getDepartureTime());
        assertEquals(LocalDateTime.of(2024, 9, 30, 17, 30), createdFlight.getArrivalTime());
        assertEquals(EFlightAirplane.Boeing_737, createdFlight.getFlightAirplane());
        assertEquals(180, createdFlight.getCapacityPlane());
        assertTrue(createdFlight.isAvailability());
        assertEquals(30, createdFlight.getNumRows());
        assertEquals(new BigDecimal("300.00"), createdFlight.getFlightPrice());
        assertTrue(createdFlight.getSeats().isEmpty());
        assertTrue(createdFlight.getBookingList().isEmpty());
        //assertTrue(createdFlight.getAirports().isEmpty());

        verify(flightRepository, times(1)).save(any(Flight.class));
    }

    @Test
    public void testGetAllFlights() {
        List<Flight> flights = new ArrayList<>();
        flights.add(flight1);
        flights.add(flight2);

        when(flightRepository.findAll()).thenReturn(flights);

        List<Flight> allFlights = flightService.getAllFlights();

        assertNotNull(allFlights);
        assertEquals(2, allFlights.size());

        Flight firstFlight = allFlights.get(0);
        assertEquals(1L, firstFlight.getFlightId());
        assertEquals(123, firstFlight.getFlightNumber());
        assertEquals(LocalDateTime.of(2024, 9, 30, 14, 0), firstFlight.getDepartureTime());
        assertEquals(LocalDateTime.of(2024, 9, 30, 17, 30), firstFlight.getArrivalTime());
        assertEquals(EFlightAirplane.Boeing_737, firstFlight.getFlightAirplane());
        assertEquals(180, firstFlight.getCapacityPlane());
        assertTrue(firstFlight.isAvailability());
        assertEquals(30, firstFlight.getNumRows());
        assertEquals(new BigDecimal("300.00"), firstFlight.getFlightPrice());
        assertTrue(firstFlight.getSeats().isEmpty());
        assertTrue(firstFlight.getBookingList().isEmpty());
        //assertTrue(firstFlight.getAirports().isEmpty());

        Flight secondFlight = allFlights.get(1);
        assertEquals(2L, secondFlight.getFlightId());
        assertEquals(456, secondFlight.getFlightNumber());
        assertEquals(LocalDateTime.of(2024, 10, 1, 9, 0), secondFlight.getDepartureTime());
        assertEquals(LocalDateTime.of(2024, 10, 1, 12, 0), secondFlight.getArrivalTime());
        assertEquals(EFlightAirplane.Airbus_A321, secondFlight.getFlightAirplane());
        assertEquals(160, secondFlight.getCapacityPlane());
        assertTrue(secondFlight.isAvailability());
        assertEquals(28, secondFlight.getNumRows());
        assertEquals(new BigDecimal("200.00"), secondFlight.getFlightPrice());
        assertTrue(secondFlight.getSeats().isEmpty());
        assertTrue(secondFlight.getBookingList().isEmpty());
        //assertTrue(secondFlight.getAirports().isEmpty());

        verify(flightRepository, times(1)).findAll();
    }

    @Test
    public void testGetFlightById() {
        when(flightRepository.findById(1L)).thenReturn(Optional.of(flight1));

        Flight foundFlight = flightService.getFlightById(1L);

        assertNotNull(foundFlight);
        assertEquals(1L, foundFlight.getFlightId());
        assertEquals(123, foundFlight.getFlightNumber());
        assertEquals(LocalDateTime.of(2024, 9, 30, 14, 0), foundFlight.getDepartureTime());
        assertEquals(LocalDateTime.of(2024, 9, 30, 17, 30), foundFlight.getArrivalTime());
        assertEquals(EFlightAirplane.Boeing_737, foundFlight.getFlightAirplane());
        assertEquals(180, foundFlight.getCapacityPlane());
        assertTrue(foundFlight.isAvailability());
        assertEquals(30, foundFlight.getNumRows());
        assertEquals(new BigDecimal("300.00"), foundFlight.getFlightPrice());
        assertTrue(foundFlight.getSeats().isEmpty());
        assertTrue(foundFlight.getBookingList().isEmpty());
        //assertTrue(foundFlight.getAirports().isEmpty());

        verify(flightRepository, times(1)).findById(1L);
    }

    @Test
    public void testUpdateFlight() {
        when(flightRepository.existsById(1L)).thenReturn(true);
        when(flightRepository.save(any(Flight.class))).thenReturn(flight1);

        Flight updatedFlight = flightService.updateFlight(1L, flight1);

        assertNotNull(updatedFlight);
        assertEquals(1L, updatedFlight.getFlightId());
        assertEquals(123, updatedFlight.getFlightNumber());
        assertEquals(LocalDateTime.of(2024, 9, 30, 14, 0), updatedFlight.getDepartureTime());
        assertEquals(LocalDateTime.of(2024, 9, 30, 17, 30), updatedFlight.getArrivalTime());
        assertEquals(EFlightAirplane.Boeing_737, updatedFlight.getFlightAirplane());
        assertEquals(180, updatedFlight.getCapacityPlane());
        assertTrue(updatedFlight.isAvailability());
        assertEquals(30, updatedFlight.getNumRows());
        assertEquals(new BigDecimal("300.00"), updatedFlight.getFlightPrice());
        assertTrue(updatedFlight.getSeats().isEmpty());
        assertTrue(updatedFlight.getBookingList().isEmpty());
        //assertTrue(updatedFlight.getAirports().isEmpty());

        verify(flightRepository, times(1)).save(any(Flight.class));
    }

    @Test
    public void testDeleteFlight() {
        when(flightRepository.existsById(1L)).thenReturn(true);

        boolean isDeleted = flightService.deleteFlight(1L);

        assertTrue(isDeleted);
        verify(flightRepository, times(1)).deleteById(1L);
    }

    @Test
    public void testDeleteFlight_NotFound() {
        when(flightRepository.existsById(1L)).thenReturn(false);

        boolean isDeleted = flightService.deleteFlight(1L);

        assertFalse(isDeleted);
        verify(flightRepository, times(0)).deleteById(1L);
    }
}
