package com.flightbookings.flight_bookings.controllers;

import com.flightbookings.flight_bookings.models.AirportEntity;
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
/**
 * Test class for {@link AirportController}.
 * Verifies the behavior of Airport-related endpoints.
 */
class AirportEntityControllerTest {
    @Mock
    private AirportService airportService;

    @InjectMocks
    private AirportController airportController;
    /**
     * Initializes mocks before each test.
     */
    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }
    /**
     * Tests the retrieval of all airports.
     * Verifies that the correct status and number of airports are returned.
     */
    @Test
    void testGetAllAirports() {
        // Arrange
        List<AirportEntity> airportList = new ArrayList<>();
        airportList.add(new AirportEntity("MAD", "Madrid-Barajas", "Madrid", ECountry.ESPAÑA));
        airportList.add(new AirportEntity("BCN", "Barcelona-El Prat", "Barcelona", ECountry.ESPAÑA));

        when(airportService.getAllAirports()).thenReturn(airportList);

        // Act
        ResponseEntity<List<AirportEntity>> response = airportController.getAllAirports();

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(2, response.getBody().size());
        verify(airportService, times(1)).getAllAirports();
    }
    /**
     * Tests the creation of a new airport.
     * Verifies that the created airport has the expected properties.
     */
    @Test
    void testCreateAirport() {
        // Arrange
        AirportEntity airport = new AirportEntity("MAD", "Madrid-Barajas", "Madrid", ECountry.ESPAÑA);
        when(airportService.createAirport(any(AirportEntity.class))).thenReturn(airport);

        // Act
        ResponseEntity<AirportEntity> response = airportController.createAirport(airport);

        // Assert
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(airport.getAirportCode(), response.getBody().getAirportCode());
        assertEquals(airport.getAirportName(), response.getBody().getAirportName());
        assertEquals(airport.getAirportCity(), response.getBody().getAirportCity());
        assertEquals(airport.getAirportCountry(), response.getBody().getAirportCountry());

        ArgumentCaptor<AirportEntity> argumentCaptor = ArgumentCaptor.forClass(AirportEntity.class);
        verify(airportService, times(1)).createAirport(argumentCaptor.capture());
        assertEquals(airport.getAirportCode(), argumentCaptor.getValue().getAirportCode());
    }

}