package com.flightbookings.flight_bookings.services;

import com.flightbookings.flight_bookings.models.Airport;
import com.flightbookings.flight_bookings.repositories.IAirportRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class AirportServiceImplTest {

    @InjectMocks
    private AirportServiceImpl airportService;

    @Mock
    private IAirportRepository airportRepository;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testCreateAirport() {
        Airport airport = new Airport(null, "MAD", "Madrid Barajas Airport", "Madrid", "Spain");
        Airport savedAirport = new Airport(1L, "MAD", "Madrid Barajas Airport", "Madrid", "Spain");

        when(airportRepository.save(any(Airport.class))).thenReturn(savedAirport);

        Airport result = airportService.createAirport(airport);

        assertEquals(savedAirport, result);
        verify(airportRepository, times(1)).save(airport);
    }

    @Test
    public void testGetAllAirports() {
        Airport airport1 = new Airport(1L, "MAD", "Madrid Barajas Airport", "Madrid", "Spain");
        Airport airport2 = new Airport(2L, "BOG", "El Dorado International Airport", "Bogotá", "Colombia");
        List<Airport> airports = Arrays.asList(airport1, airport2);

        when(airportRepository.findAll()).thenReturn(airports);

        List<Airport> result = airportService.getAllAirports();

        assertEquals(2, result.size());
        assertEquals(airports, result);
        verify(airportRepository, times(1)).findAll();
    }

    @Test
    public void testGetAirportByIdFound() {
        Airport airport = new Airport(1L, "MAD", "Madrid Barajas Airport", "Madrid", "Spain");

        when(airportRepository.findById(1L)).thenReturn(Optional.of(airport));

        Optional<Airport> result = airportService.getAirportById(1L);

        assertEquals(airport, result.get());
        verify(airportRepository, times(1)).findById(1L);
    }

    @Test
    public void testGetAirportByIdNotFound() {
        when(airportRepository.findById(1L)).thenReturn(Optional.empty());

        Optional<Airport> result = airportService.getAirportById(1L);

        assertEquals(Optional.empty(), result);
        verify(airportRepository, times(1)).findById(1L);
    }

    @Test
    public void testUpdateAirport() {
        Airport airportToUpdate = new Airport(1L, "MAD", "Madrid Barajas Airport", "Madrid", "Spain");
        when(airportRepository.save(any(Airport.class))).thenReturn(airportToUpdate);

        Airport result = airportService.updateAirport(airportToUpdate, 1L);

        assertEquals(airportToUpdate, result);
        verify(airportRepository, times(1)).save(airportToUpdate);
    }
}