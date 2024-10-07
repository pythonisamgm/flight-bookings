package com.flightbookings.flight_bookings.controllers;

import com.flightbookings.flight_bookings.models.Airport;
import com.flightbookings.flight_bookings.models.ECountry;
import com.flightbookings.flight_bookings.services.interfaces.AirportService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class AirportControllerTest {
    @Mock
    private AirportService airportService;

    @InjectMocks
    private AirportController airportController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

//    @Test
//    void testGetAllAirports() {
//        // Arrange
//        List<Airport> airportList = new ArrayList<>();
//        airportList.add(new Airport("MAD", "Madrid-Barajas", "Madrid", ECountry.ESPAÑA));
//        airportList.add(new Airport("BCN", "Barcelona-El Prat", "Barcelona", ECountry.ESPAÑA));
//
//        when(airportService.getAllAirports()).thenReturn(airportList);
//
//        // Act
//        ResponseEntity<List<Airport>> response = airportController.getAllAirports();
//
//        // Assert
//        assertEquals(HttpStatus.OK, response.getStatusCode());
//        assertNotNull(response.getBody());
//        assertEquals(2, response.getBody().size());
//        verify(airportService, times(1)).getAllAirports();
//    }

//    @Test
//    void testCreateAirport() {
//        // Arrange
//        Airport airport = new Airport("MAD", "Madrid-Barajas", "Madrid", ECountry.ESPAÑA);
//        when(airportService.createAirport(any(Airport.class))).thenReturn(airport);
//
//        // Act
//        ResponseEntity<Airport> response = airportController.createAirport(airport);
//
//        // Assert
//        assertEquals(HttpStatus.CREATED, response.getStatusCode());
//        assertNotNull(response.getBody());
//        assertEquals(airport.getAirportCode(), response.getBody().getAirportCode());
//        assertEquals(airport.getAirportName(), response.getBody().getAirportName());
//        assertEquals(airport.getAirportCity(), response.getBody().getAirportCity());
//        assertEquals(airport.getAirportCountry(), response.getBody().getAirportCountry());
//
//        ArgumentCaptor<Airport> argumentCaptor = ArgumentCaptor.forClass(Airport.class);
//        verify(airportService, times(1)).createAirport(argumentCaptor.capture());
//        assertEquals(airport.getAirportCode(), argumentCaptor.getValue().getAirportCode());
//    }

}