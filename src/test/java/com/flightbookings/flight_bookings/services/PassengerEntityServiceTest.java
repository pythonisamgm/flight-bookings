package com.flightbookings.flight_bookings.services;

import com.flightbookings.flight_bookings.models.PassengerEntity;
import com.flightbookings.flight_bookings.repositories.IPassengerRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class PassengerEntityServiceTest {

    @Mock
    private IPassengerRepository passengerRepository;

    @InjectMocks
    private PassengerServiceImpl passengerService;

    private PassengerEntity passenger1;
    private PassengerEntity passenger2;
    private List<PassengerEntity> passengerList;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);

        passenger1 = new PassengerEntity();
        passenger1.setPassengerId(1L);
        passenger1.setPassengerName("Juan Antonio");
        passenger1.setIdentityDoc("1337");
        passenger1.setTelephone(661777777L);
        passenger1.setNationality("Irlandés");

        passenger2 = new PassengerEntity();
        passenger2.setPassengerId(2L);
        passenger2.setPassengerName("Miguel Angel");
        passenger2.setIdentityDoc("7823");
        passenger2.setTelephone(661888888L);
        passenger2.setNationality("Alemán");

        passengerList = new ArrayList<>(Arrays.asList(passenger1, passenger2));
    }

    @Test
    public void testCreatePassenger() {
        when(passengerRepository.save(any(PassengerEntity.class))).thenReturn(passenger1);

        PassengerEntity createdPassenger = passengerService.createPassenger(passenger1);

        assertNotNull(createdPassenger);
        assertEquals(1L, createdPassenger.getPassengerId());
        assertEquals("Juan Antonio", createdPassenger.getPassengerName());

        verify(passengerRepository, times(1)).save(any(PassengerEntity.class));
    }

    @Test
    public void testGetPassengerById() {
        when(passengerRepository.findById(1L)).thenReturn(Optional.of(passenger1));

        PassengerEntity foundPassenger = passengerService.getPassengerById(1L);

        assertNotNull(foundPassenger);
        assertEquals("Juan Antonio", foundPassenger.getPassengerName());

        verify(passengerRepository, times(1)).findById(1L);
    }

    @Test
    public void testGetPassengerById_NotFound() {
        when(passengerRepository.findById(3L)).thenReturn(Optional.empty());

        PassengerEntity foundPassenger = passengerService.getPassengerById(3L);

        assertNull(foundPassenger);
        verify(passengerRepository, times(1)).findById(3L);
    }

    @Test
    public void testGetAllPassengers() {
        when(passengerRepository.findAll()).thenReturn(passengerList);

        List<PassengerEntity> passengers = passengerService.getAllPassengers();

        assertEquals(2, passengers.size());
        assertEquals("Juan Antonio", passengers.get(0).getPassengerName());
        assertEquals("Miguel Angel", passengers.get(1).getPassengerName());

        verify(passengerRepository, times(1)).findAll();
    }

    @Test
    public void testUpdatePassenger() {
        when(passengerRepository.findById(1L)).thenReturn(Optional.of(passenger1));
        when(passengerRepository.save(any(PassengerEntity.class))).thenReturn(passenger1);

        PassengerEntity updatedPassenger = passengerService.updatePassenger(1L, passenger1);

        assertNotNull(updatedPassenger);
        assertEquals("Juan Antonio", updatedPassenger.getPassengerName());

        verify(passengerRepository, times(1)).findById(1L);
        verify(passengerRepository, times(1)).save(any(PassengerEntity.class));
    }

    @Test
    public void testUpdatePassenger_NotFound() {
        when(passengerRepository.findById(3L)).thenReturn(Optional.empty());

        PassengerEntity updatedPassenger = passengerService.updatePassenger(3L, passenger1);

        assertNull(updatedPassenger);
        verify(passengerRepository, times(1)).findById(3L);
    }

    @Test
    public void testDeletePassenger() {
        when(passengerRepository.findById(1L)).thenReturn(Optional.of(passenger1));

        boolean isDeleted = passengerService.deletePassenger(1L);

        assertTrue(isDeleted);
        verify(passengerRepository, times(1)).findById(1L);
        verify(passengerRepository, times(1)).deleteById(1L);
    }

    @Test
    public void testDeletePassenger_NotFound() {
        when(passengerRepository.findById(4L)).thenReturn(Optional.empty());

        boolean isDeleted = passengerService.deletePassenger(4L);

        assertFalse(isDeleted);
        verify(passengerRepository, times(1)).findById(4L);
        verify(passengerRepository, never()).delete(any(PassengerEntity.class));
    }
}