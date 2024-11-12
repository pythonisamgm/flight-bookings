package com.flightbookings.flight_bookings.services;

import com.flightbookings.flight_bookings.models.AirportEntity;
import com.flightbookings.flight_bookings.models.ECountry;
import com.flightbookings.flight_bookings.repositories.IAirportRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class AirportEntityServiceImplTest {

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
        AirportEntity airport = new AirportEntity("MAD", "Madrid-Barajas Adolfo Suárez", "Madrid", ECountry.ESPAÑA);
        when(airportRepository.save(any(AirportEntity.class))).thenReturn(airport);

        // Act
        AirportEntity createdAirport = airportService.createAirport(airport);

        // Assert
        assertNotNull(createdAirport);
        assertEquals("MAD", createdAirport.getAirportCode());
        verify(airportRepository, times(1)).save(airport);
    }

    @Test
    void testCreateAirports() {
        // Arrange
        AirportEntity airport1 = new AirportEntity("BCN", "Barcelona-El Prat", "Barcelona", ECountry.ESPAÑA);
        AirportEntity airport2 = new AirportEntity("VLC", "Valencia", "Valencia", ECountry.ESPAÑA);
        Set<AirportEntity> airports = Set.of(airport1, airport2);
        when(airportRepository.saveAll(any(Set.class))).thenReturn(Arrays.asList(airport1, airport2));

        // Act
        List<AirportEntity> createdAirports = airportService.createAirports(airports);

        // Assert
        assertNotNull(createdAirports);
        assertEquals(2, createdAirports.size());
        assertEquals("BCN", createdAirports.get(0).getAirportCode());
        verify(airportRepository, times(1)).saveAll(airports);
    }

    @Test
    void testGetAllAirports() {
        // Arrange
        AirportEntity airport = new AirportEntity("MAD", "Madrid-Barajas Adolfo Suárez", "Madrid", ECountry.ESPAÑA);
        when(airportRepository.findAll()).thenReturn(List.of(airport));

        // Act
        List<AirportEntity> airports = airportService.getAllAirports();

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
        AirportEntity airport = new AirportEntity(airportCode, "Madrid-Barajas Adolfo Suárez", "Madrid", ECountry.ESPAÑA);
        when(airportRepository.findById(airportCode)).thenReturn(Optional.of(airport));

        // Act
        Optional<AirportEntity> foundAirport = airportService.getAirportById(airportCode);

        // Assert
        assertTrue(foundAirport.isPresent());
        assertEquals("MAD", foundAirport.get().getAirportCode());
        verify(airportRepository, times(1)).findById(airportCode);
    }

}