package com.flightbookings.flight_bookings.controllers;

import com.flightbookings.flight_bookings.models.Airport;
import com.flightbookings.flight_bookings.services.interfaces.AirportService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class AirportControllerTest {
    @InjectMocks
    private AirportController airportController;

    @Mock
    private AirportService airportService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testCreateAirport() {
        Airport airport = new Airport(1L, "JFK", "John F. Kennedy International Airport", "New York", "USA");
        when(airportService.createAirport(any(Airport.class))).thenReturn(airport);

        ResponseEntity<Airport> response = airportController.createAirport(airport);

        assertEquals(200, response.getStatusCodeValue());
        assertEquals(airport, response.getBody());
        verify(airportService, times(1)).createAirport(any(Airport.class));
    }

    @Test
    public void testGetAirportByIdFound() {
        Airport airport = new Airport(1L, "JFK", "John F. Kennedy International Airport", "New York", "USA");
        when(airportService.getAirportById(1L)).thenReturn(Optional.of(airport));

        ResponseEntity<Airport> response = airportController.getAirportById(1L);

        assertEquals(200, response.getStatusCodeValue());
        assertEquals(airport, response.getBody());
        verify(airportService, times(1)).getAirportById(1L);
    }

    @Test
    public void testGetAirportByIdNotFound() {
        when(airportService.getAirportById(1L)).thenReturn(Optional.empty());

        ResponseEntity<Airport> response = airportController.getAirportById(1L);

        assertEquals(404, response.getStatusCodeValue());
        verify(airportService, times(1)).getAirportById(1L);
    }

    @Test
    public void testGetAllAirports() {
        Airport airport1 = new Airport(1L, "JFK", "John F. Kennedy International Airport", "New York", "USA");
        Airport airport2 = new Airport(2L, "LAX", "Los Angeles International Airport", "Los Angeles", "USA");
        List<Airport> airports = Arrays.asList(airport1, airport2);
        when(airportService.getAllAirports()).thenReturn(airports);

        ResponseEntity<List<Airport>> response = airportController.getAllAirports();

        assertEquals(200, response.getStatusCodeValue());
        assertEquals(airports, response.getBody());
        verify(airportService, times(1)).getAllAirports();
    }

    @Test
    public void testUpdateAirport() {
        Airport airport = new Airport(1L, "JFK", "John F. Kennedy International Airport", "New York", "USA");
        when(airportService.updateAirport(any(Airport.class), eq(1L))).thenReturn(airport);

        ResponseEntity<Airport> response = airportController.updateAirport(airport, 1L);

        assertEquals(200, response.getStatusCodeValue());
        assertEquals(airport, response.getBody());
        verify(airportService, times(1)).updateAirport(any(Airport.class), eq(1L));
    }
}