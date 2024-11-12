package com.flightbookings.flight_bookings.services;

import com.flightbookings.flight_bookings.exceptions.SeatAlreadyBookedException;
import com.flightbookings.flight_bookings.exceptions.SeatNotFoundException;
import com.flightbookings.flight_bookings.models.ESeatLetter;
import com.flightbookings.flight_bookings.models.FlightEntity;
import com.flightbookings.flight_bookings.models.SeatEntity;
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

class SeatEntityServiceImplTest {

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
        FlightEntity flight = new FlightEntity();
        int numRows = 2;
        List<SeatEntity> seats = new ArrayList<>();
        for (int i = 1; i <= numRows; i++) {
            for (ESeatLetter letter : ESeatLetter.values()) {
                SeatEntity seat = new SeatEntity(null, i, letter, false, flight, null);
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
        FlightEntity flight = new FlightEntity();
        SeatEntity seat = new SeatEntity(null, 1, ESeatLetter.A, false, flight, null);
        seat.setSeatName("1A");

        when(seatRepository.findByFlightAndSeatName(flight, "1A")).thenReturn(Optional.of(seat));
        when(seatRepository.save(seat)).thenReturn(seat);

        SeatEntity reservedSeat = seatService.reserveSeat(flight, "1A");

        assertNotNull(reservedSeat);
        assertTrue(reservedSeat.isBooked());
        assertEquals("1A", reservedSeat.getSeatName());

        verify(seatRepository, times(1)).findByFlightAndSeatName(flight, "1A");
        verify(seatRepository, times(1)).save(seat);
    }
    @Test
    void testReserveSeatThrowsSeatNotFoundException() {
        FlightEntity flight = new FlightEntity();

        when(seatRepository.findByFlightAndSeatName(flight, "1A")).thenReturn(Optional.empty());

        assertThrows(SeatNotFoundException.class, () -> {
            seatService.reserveSeat(flight, "1A");
        });

        verify(seatRepository, times(1)).findByFlightAndSeatName(flight, "1A");
        verify(seatRepository, times(0)).save(any(SeatEntity.class));
    }

    @Test
    void testReserveSeatThrowsSeatAlreadyBookedException() {
        FlightEntity flight = new FlightEntity();
        SeatEntity seat = new SeatEntity(null, 1, ESeatLetter.A, true, flight, null); // Seat already booked
        seat.setSeatName("1A");

        when(seatRepository.findByFlightAndSeatName(flight, "1A")).thenReturn(Optional.of(seat));

        assertThrows(SeatAlreadyBookedException.class, () -> {
            seatService.reserveSeat(flight, "1A");
        });

        verify(seatRepository, times(1)).findByFlightAndSeatName(flight, "1A");
        verify(seatRepository, times(0)).save(any(SeatEntity.class));
    }
}
