package com.flightbookings.flight_bookings.services;

import com.flightbookings.flight_bookings.exceptions.SeatAlreadyBookedException;
import com.flightbookings.flight_bookings.exceptions.SeatNotFoundException;
import com.flightbookings.flight_bookings.models.ESeatLetter;
import com.flightbookings.flight_bookings.models.Flight;
import com.flightbookings.flight_bookings.models.Seat;
import com.flightbookings.flight_bookings.repositories.ISeatRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class SeatServiceImplTest {

    @Mock
    private ISeatRepository seatRepository;

    @InjectMocks
    private SeatServiceImpl seatService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void test_if_initializedSeats_creates_seats_and_rename_them() {
        Flight flight = new Flight();
        int numRows = 2;
        List<Seat> seats = new ArrayList<>();
        for (int i = 1; i <= numRows; i++) {
            for (ESeatLetter letter : ESeatLetter.values()) {
                Seat seat = new Seat(null, i, letter, false, flight, null);
                seat.setSeatName(i + letter.name());
                seats.add(seat);
            }
        }

        when(seatRepository.saveAll(anyList())).thenReturn(seats);

        List<String> result = seatService.initializeSeats(flight, numRows);

        assertEquals(12, result.size());
        assertTrue(result.contains("1A"));
        assertTrue(result.contains("2F"));

        verify(seatRepository, times(1)).saveAll(anyList());
    }
    @Test
    void test_if_reserveSeat_save_seat_in_repo() {
        Flight flight = new Flight();
        Seat seat = new Seat(null, 1, ESeatLetter.A, false, flight, null);
        seat.setSeatName("1A");

        when(seatRepository.findBySeatReference(flight, "1A")).thenReturn(Optional.of(seat));
        when(seatRepository.save(seat)).thenReturn(seat);

        Seat reservedSeat = seatService.reserveSeat(flight, "1A");

        assertNotNull(reservedSeat);
        assertTrue(reservedSeat.isBooked());
        assertEquals("1A", reservedSeat.getSeatName());

        verify(seatRepository, times(1)).findBySeatReference(flight, "1A");
        verify(seatRepository, times(1)).save(seat);
    }
}
