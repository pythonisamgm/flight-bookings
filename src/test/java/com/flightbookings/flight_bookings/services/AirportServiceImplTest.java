package com.flightbookings.flight_bookings.services;

import com.flightbookings.flight_bookings.models.Airport;
import com.flightbookings.flight_bookings.models.ECountry;
import com.flightbookings.flight_bookings.repositories.IAirportRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class AirportServiceImplTest {

    private IAirportRepository airportRepository;
    private AirportServiceImpl airportService;

    @BeforeEach
    void setUp() {
        airportRepository = mock(IAirportRepository.class);
        airportService = new AirportServiceImpl(airportRepository);
    }

    @Test
    void testCreateAirport() {
        // Arrange
        Airport airport = new Airport("MAD", "Madrid-Barajas Adolfo Suárez", "Madrid", ECountry.ESPAÑA);
        when(airportRepository.save(any(Airport.class))).thenReturn(airport);

        // Act
        Airport createdAirport = airportService.createAirport(airport);

        // Assert
        assertNotNull(createdAirport);
        assertEquals("MAD", createdAirport.getAirportCode());
        verify(airportRepository, times(1)).save(airport);
    }

    @Test
    void testCreateAirports() {
        // Arrange
        Airport airport1 = new Airport("BCN", "Barcelona-El Prat", "Barcelona", ECountry.ESPAÑA);
        Airport airport2 = new Airport("VLC", "Valencia", "Valencia", ECountry.ESPAÑA);
        Set<Airport> airports = Set.of(airport1, airport2);
        when(airportRepository.saveAll(any(Set.class))).thenReturn(Arrays.asList(airport1, airport2));

        // Act
        List<Airport> createdAirports = airportService.createAirports(airports);

        // Assert
        assertNotNull(createdAirports);
        assertEquals(2, createdAirports.size());
        assertEquals("BCN", createdAirports.get(0).getAirportCode());
        verify(airportRepository, times(1)).saveAll(airports);
    }

    @Test
    void testGetAllAirports() {
        // Arrange
        Airport airport = new Airport("MAD", "Madrid-Barajas Adolfo Suárez", "Madrid", ECountry.ESPAÑA);
        when(airportRepository.findAll()).thenReturn(List.of(airport));

        // Act
        List<Airport> airports = airportService.getAllAirports();

        // Assert
        assertNotNull(airports);
        assertEquals(1, airports.size());
        assertEquals("MAD", airports.get(0).getAirportCode());
        verify(airportRepository, times(1)).findAll();
    }

    @Test
    void testGetAirportById() {
        // Arrange
        String airportCode = "MAD";
        Airport airport = new Airport(airportCode, "Madrid-Barajas Adolfo Suárez", "Madrid", ECountry.ESPAÑA);
        when(airportRepository.findById(airportCode)).thenReturn(Optional.of(airport));

        // Act
        Optional<Airport> foundAirport = airportService.getAirportById(airportCode);

        // Assert
        assertTrue(foundAirport.isPresent());
        assertEquals("MAD", foundAirport.get().getAirportCode());
        verify(airportRepository, times(1)).findById(airportCode);
    }

}