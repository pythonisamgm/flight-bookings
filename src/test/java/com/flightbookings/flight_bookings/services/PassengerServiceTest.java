package com.flightbookings.flight_bookings.services;

import com.flightbookings.flight_bookings.models.Passenger;
import com.flightbookings.flight_bookings.repositories.IPassengerRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class PassengerServiceTest {

    @Mock
    private IPassengerRepository passengerRepository;

    @InjectMocks
    private PassengerService passengerService;

    private Passenger passenger;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        passenger = new Passenger();
        passenger.setPassengerId(1L);
        passenger.setPassengerName("Juan Antonio");
    }

    @Test
    public void CreatePassengerTest() {
        when(passengerRepository.save(passenger)).thenReturn(passenger);
        Passenger savedPassenger = passengerService.createPassenger(passenger);
        assertNotNull(savedPassenger);
        assertEquals(1L, savedPassenger.getPassengerId());
        verify(passengerRepository, times(1)).save(passenger);
    }

    @Test
    public void GetPassengerByIdTest() {
        when(passengerRepository.findById(1L)).thenReturn(Optional.of(passenger));
        Passenger foundPassenger = passengerService.getPassengerById(1L);
        assertNotNull(foundPassenger);
        assertEquals(1L, foundPassenger.getPassengerId());
        verify(passengerRepository, times(1)).findById(1L);
    }

    @Test
    public void GetAllPassengersTest() {
        when(passengerRepository.findAll()).thenReturn(Arrays.asList(passenger));
        List<Passenger> passengers = passengerService.getAllPassengers();
        assertEquals(1, passengers.size());
        verify(passengerRepository, times(1)).findAll();
    }

    @Test
    public void UpdatePassengerTest() {
        when(passengerRepository.findById(1L)).thenReturn(Optional.of(passenger));
        when(passengerRepository.save(passenger)).thenReturn(passenger);
        Passenger updatedPassenger = passengerService.updatePassenger(1L, passenger);
        assertNotNull(updatedPassenger);
        assertEquals(1L, updatedPassenger.getPassengerId());
        verify(passengerRepository, times(1)).save(passenger);
    }

    @Test
    public void DeletePassengerTest() {
        when(passengerRepository.findById(1L)).thenReturn(Optional.of(passenger));
        boolean isDeleted = passengerService.deletePassenger(1L);
        assertTrue(isDeleted);
        verify(passengerRepository, times(1)).deleteById(1L);
    }
}