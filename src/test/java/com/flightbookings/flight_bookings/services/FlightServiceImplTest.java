package com.flightbookings.flight_bookings.services;

import com.flightbookings.flight_bookings.exceptions.FlightNotFoundException;
import com.flightbookings.flight_bookings.models.Flight;
import com.flightbookings.flight_bookings.models.EFlightAirplane;
import com.flightbookings.flight_bookings.models.Seat;
import com.flightbookings.flight_bookings.repositories.IFlightRepository;
import com.flightbookings.flight_bookings.repositories.ISeatRepository;
import com.flightbookings.flight_bookings.services.interfaces.FlightDurationService;
import com.flightbookings.flight_bookings.services.interfaces.SeatService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.math.BigDecimal;
import java.time.Duration;
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
    private FlightDurationService flightDurationService;

    @Mock
    private SeatService seatService;
    @Mock
    private ISeatRepository seatRepository;

    private Flight flight;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        flight = new Flight();
        flight.setFlightId(1L);
        flight.setFlightNumber(123);
        flight.setDepartureTime(LocalDateTime.of(2024, 10, 12, 14, 0));
        flight.setArrivalTime(LocalDateTime.of(2024, 10, 12, 16, 0));
        flight.setFlightAirplane(EFlightAirplane.Boeing_747);
        flight.setCapacityPlane(200);
        flight.setAvailability(true);
        flight.setNumRows(20);
        flight.setFlightPrice(BigDecimal.valueOf(300));
        flight.setFlightDuration(120);
    }

    @Test
    void testCreateFlight() {
        int durationMinutes = 120;
        List<String> mockSeatIdentifiers = List.of("1A", "1B", "1C");
        List<Seat> mockSeats = new ArrayList<>();

        when(flightRepository.save(any(Flight.class))).thenReturn(flight);
        when(flightDurationService.calculateFlightDuration(any(Flight.class))).thenReturn(durationMinutes);
        when(seatService.initializeSeats(any(Flight.class), anyInt())).thenReturn(mockSeatIdentifiers);
        when(seatRepository.findByFlight(any(Flight.class))).thenReturn(mockSeats);

        Flight createdFlight = flightService.createFlight(flight);

        assertNotNull(createdFlight);
        assertEquals(flight.getFlightId(), createdFlight.getFlightId());
        assertEquals(durationMinutes, createdFlight.getFlightDuration());
        assertEquals(mockSeats, createdFlight.getSeats());
        verify(flightRepository).save(flight);
        verify(seatService).initializeSeats(createdFlight, flight.getNumRows());
        verify(seatRepository).findByFlight(createdFlight);
    }

    @Test
    void testGetFlightById() {
        when(flightRepository.findById(1L)).thenReturn(Optional.of(flight));

        Flight foundFlight = flightService.getFlightById(1L);

        assertNotNull(foundFlight);
        assertEquals(flight.getFlightId(), foundFlight.getFlightId());
        verify(flightRepository).findById(1L);
    }

    @Test
    void testGetAllFlights() {
        List<Flight> flights = new ArrayList<>();
        flights.add(flight);
        when(flightRepository.findAll()).thenReturn(flights);

        List<Flight> allFlights = flightService.getAllFlights();

        assertFalse(allFlights.isEmpty());
        assertEquals(1, allFlights.size());
        assertEquals(flight.getFlightId(), allFlights.get(0).getFlightId());
        verify(flightRepository).findAll();
    }

    @Test
    void testUpdateFlight() {
        Flight updatedFlight = new Flight();
        updatedFlight.setFlightNumber(124);
        updatedFlight.setDepartureTime(LocalDateTime.of(2024, 10, 12, 15, 0));
        updatedFlight.setArrivalTime(LocalDateTime.of(2024, 10, 12, 17, 0));
        updatedFlight.setFlightAirplane(EFlightAirplane.Boeing_777);
        updatedFlight.setCapacityPlane(250);
        updatedFlight.setAvailability(false);
        updatedFlight.setNumRows(25);
        updatedFlight.setFlightPrice(BigDecimal.valueOf(350));

        when(flightRepository.existsById(1L)).thenReturn(true);
        when(flightRepository.save(any(Flight.class))).thenReturn(updatedFlight);
        when(flightDurationService.calculateFlightDuration(any(Flight.class))).thenReturn(120);

        Flight result = flightService.updateFlight(1L, updatedFlight);

        assertNotNull(result);
        assertEquals(124, result.getFlightNumber());
        assertEquals(120, result.getFlightDuration());

        verify(flightRepository).existsById(1L);
        verify(flightRepository).save(updatedFlight);
        verify(flightDurationService).calculateFlightDuration(updatedFlight);
    }

    @Test
    void test_DeleteFlight_Success() {
        Long flightId = 1L;

        when(flightRepository.existsById(flightId)).thenReturn(true);
        doNothing().when(flightRepository).deleteById(flightId);

        String result = flightService.deleteFlight(flightId);

        assertEquals("Flight successfully deleted", result);
        verify(flightRepository, times(1)).deleteById(flightId);
    }

    @Test
    void test_DeleteFlight_NotFound() {
        Long flightId = 1L;

        when(flightRepository.existsById(flightId)).thenReturn(false);

        assertThrows(FlightNotFoundException.class, () -> flightService.deleteFlight(flightId));
        verify(flightRepository, never()).deleteById(flightId);
    }

    @Test
    void testDelayFlight() {
        LocalDateTime newDepartureTime = LocalDateTime.of(2024, 10, 12, 15, 30);

        when(flightRepository.findById(1L)).thenReturn(Optional.of(flight));

        flightService.delayFlight(1L, newDepartureTime);

        assertEquals(newDepartureTime, flight.getDepartureTime());
        verify(flightRepository).save(flight);
        verify(flightRepository).findById(1L);
    }
    @Test
    void testGetFlightsByAirplaneType() {
        Flight flight1 = new Flight();
        flight1.setFlightAirplane(EFlightAirplane.Boeing_747);

        Flight flight2 = new Flight();
        flight2.setFlightAirplane(EFlightAirplane.Airbus_A320);

        List<Flight> flights = List.of(flight1, flight2);
        when(flightRepository.findAll()).thenReturn(flights);

        List<Flight> result = flightService.getFlightsByAirplaneType(EFlightAirplane.Boeing_747);

        assertEquals(1, result.size());
        assertEquals(EFlightAirplane.Boeing_747, result.get(0).getFlightAirplane());
        verify(flightRepository).findAll();
    }
    @Test
    void testUpdateFlightAvailability() {
        LocalDateTime now = LocalDateTime.now();

        Flight flightWithPastArrival = new Flight();
        flightWithPastArrival.setArrivalTime(now.minusHours(1));
        flightWithPastArrival.setSeats(List.of(new Seat()));
        flightWithPastArrival.setAvailability(true);

        Flight flightWithNoSeats = new Flight();
        flightWithNoSeats.setArrivalTime(now.plusHours(2));
        flightWithNoSeats.setSeats(new ArrayList<>());
        flightWithNoSeats.setAvailability(true);

        Flight flightWithFutureArrivalAndSeats = new Flight();
        flightWithFutureArrivalAndSeats.setArrivalTime(now.plusHours(2));
        flightWithFutureArrivalAndSeats.setSeats(List.of(new Seat()));
        flightWithFutureArrivalAndSeats.setAvailability(true);

        List<Flight> flights = List.of(flightWithPastArrival, flightWithNoSeats, flightWithFutureArrivalAndSeats);
        when(flightRepository.findAll()).thenReturn(flights);

        flightService.updateFlightAvailability();

        assertFalse(flightWithPastArrival.isAvailability());
        assertFalse(flightWithNoSeats.isAvailability());
        assertTrue(flightWithFutureArrivalAndSeats.isAvailability());

        verify(flightRepository).save(flightWithPastArrival);
        verify(flightRepository).save(flightWithNoSeats);
        verify(flightRepository, never()).save(flightWithFutureArrivalAndSeats);
    }
    @Test
    void testCancelFlight() {
        Flight flightToCancel = new Flight();
        flightToCancel.setAvailability(true);
        when(flightRepository.findById(1L)).thenReturn(Optional.of(flightToCancel));

        flightService.cancelFlight(1L);

        assertFalse(flightToCancel.isAvailability());
        verify(flightRepository).save(flightToCancel);
        verify(flightRepository).findById(1L);
    }


}
