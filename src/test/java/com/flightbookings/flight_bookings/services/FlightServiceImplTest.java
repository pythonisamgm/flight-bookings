package com.flightbookings.flight_bookings.services;

import com.flightbookings.flight_bookings.models.Flight;
import com.flightbookings.flight_bookings.models.EFlightAirplane;
import com.flightbookings.flight_bookings.repositories.IFlightRepository;
import com.flightbookings.flight_bookings.repositories.ISeatRepository;
import com.flightbookings.flight_bookings.services.interfaces.SeatService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.math.BigDecimal;
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
    private ISeatRepository seatRepository;

    @Mock
    private SeatService seatService;

    private Flight flight1;
    private Flight flight2;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        flight1 = new Flight();
        flight1.setFlightId(1L);
        flight1.setFlightNumber(101);
        flight1.setDepartureTime(LocalDateTime.now().plusHours(2));
        flight1.setArrivalTime(LocalDateTime.now().plusHours(5));
        flight1.setFlightAirplane(EFlightAirplane.BOEING_747);
        flight1.setCapacityPlane(200);
        flight1.setAvailability(true);
        flight1.setFlightPrice(BigDecimal.valueOf(150.00));
        flight2 = new Flight();
        flight2.setFlightId(1L);
        flight2.setFlightNumber(102);
        flight2.setDepartureTime(LocalDateTime.now().plusHours(3));
        flight2.setArrivalTime(LocalDateTime.now().plusHours(6));
        flight2.setFlightAirplane(EFlightAirplane.BOEING_777);
        flight2.setCapacityPlane(250);
        flight2.setAvailability(true);
        flight2.setFlightPrice(BigDecimal.valueOf(175.00));
    }

    @Test
    void test_Create_Flight() {
        Flight flight = new Flight();
        flight.setFlightId(1L);
        flight.setNumRows(10);

        when(flightRepository.save(any(Flight.class))).thenReturn(flight);
        when(seatService.initializeSeats(any(Flight.class), anyInt())).thenReturn(new ArrayList<>());
        when(seatRepository.findByFlight(any(Flight.class))).thenReturn(new ArrayList<>());

        Flight createdFlight = flightService.createFlight(flight);

        assertNotNull(createdFlight);
        verify(flightRepository, times(1)).save(any(Flight.class));
        verify(seatService, times(1)).initializeSeats(any(Flight.class), eq(10));
        verify(seatRepository, times(1)).findByFlight(any(Flight.class));
    }

    @Test
    void test_Get_Flight_By_Id() {
        when(flightRepository.findById(1L)).thenReturn(Optional.of(flight1));
        Flight foundFlight = flightService.getFlightById(1L);
        assertNotNull(foundFlight);
        assertEquals(flight1.getFlightNumber(), foundFlight.getFlightNumber());
    }

    @Test
    void test_Update_Flight() {
        flight2.setFlightId(1L);
        when(flightRepository.existsById(1L)).thenReturn(true);
        when(flightRepository.save(any(Flight.class))).thenReturn(flight2);
        Flight updatedFlight = flightService.updateFlight(1L, flight2);
        assertNotNull(updatedFlight);
        assertEquals(flight2.getFlightNumber(), updatedFlight.getFlightNumber());
        assertEquals(flight2.getFlightAirplane(), updatedFlight.getFlightAirplane());
        assertEquals(flight2.getFlightPrice(), updatedFlight.getFlightPrice());
    }

    @Test
    void test_Delete_Flight() {
        when(flightRepository.existsById(1L)).thenReturn(true);
        flightService.deleteFlight(1L);
        verify(flightRepository, times(1)).deleteById(1L);
    }

    @Test
    void test_Cancel_Flight() {
        when(flightRepository.findById(1L)).thenReturn(Optional.of(flight1));
        flightService.cancelFlight(1L);
        assertFalse(flight1.isAvailability());
        verify(flightRepository, times(1)).save(flight1);
    }

    @Test
    void test_Delay_Flight() {
        LocalDateTime newDepartureTime = LocalDateTime.now().plusHours(3);
        when(flightRepository.findById(1L)).thenReturn(Optional.of(flight1));
        flightService.delayFlight(1L, newDepartureTime);
        assertEquals(newDepartureTime, flight1.getDepartureTime());
        verify(flightRepository, times(1)).save(flight1);
    }

    @Test
    void test_Get_Flights_By_Airplane_Type() {
        List<Flight> flightList = new ArrayList<>();
        flightList.add(flight1);
        when(flightRepository.findAll()).thenReturn(flightList);
        List<Flight> foundFlights = flightService.getFlightsByAirplaneType(EFlightAirplane.BOEING_747);
        assertEquals(1, foundFlights.size());
        assertEquals(EFlightAirplane.BOEING_747, foundFlights.get(0).getFlightAirplane());
    }

    @Test
    void test_Get_All_Flights() {
        List<Flight> flightList = new ArrayList<>();
        flightList.add(flight1);
        flightList.add(flight2);
        when(flightRepository.findAll()).thenReturn(flightList);

        List<Flight> allFlights = flightService.getAllFlights();

        assertNotNull(allFlights);
        assertEquals(2, allFlights.size());
    }

    @Test
    void test_Update_Flight_Availability() {
        Flight flight3 = new Flight();
        flight3.setFlightId(2L);
        flight3.setFlightNumber(201);
        flight3.setDepartureTime(LocalDateTime.now().minusHours(1));
        flight3.setArrivalTime(LocalDateTime.now().minusHours(1));
        flight3.setFlightAirplane(EFlightAirplane.BOEING_777);
        flight3.setCapacityPlane(300);
        flight3.setAvailability(true);
        flight3.setFlightPrice(BigDecimal.valueOf(200.00));
        List<Flight> flightList = new ArrayList<>();
        flightList.add(flight1);
        flightList.add(flight3);
        when(flightRepository.findAll()).thenReturn(flightList);
        when(flightRepository.save(any(Flight.class))).thenAnswer(invocation -> invocation.getArgument(0));
        flightService.updateFlightAvailability();
        assertFalse(flight1.isAvailability());
        assertFalse(flight3.isAvailability());
        verify(flightRepository, times(2)).save(any(Flight.class));
    }

}