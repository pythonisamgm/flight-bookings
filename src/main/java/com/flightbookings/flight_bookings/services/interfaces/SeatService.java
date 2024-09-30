package com.flightbookings.flight_bookings.services.interfaces;

import com.flightbookings.flight_bookings.models.Flight;
import com.flightbookings.flight_bookings.models.Seat;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

public interface SeatService {

    Optional<Seat> getSeatById(Long seatId);

    @Transactional
    List<String> initializeSeats(Flight flight, int numRows);

    @Transactional
    Seat reserveSeat(Flight flight, String seatName);
}
