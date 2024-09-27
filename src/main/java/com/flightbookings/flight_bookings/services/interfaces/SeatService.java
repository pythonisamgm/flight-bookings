package com.flightbookings.flight_bookings.services.interfaces;

import com.flightbookings.flight_bookings.models.Flight;
import com.flightbookings.flight_bookings.models.Seat;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface SeatService {

    @Transactional
    List<String> initializeSeats(Flight flight, int numRows);

    @Transactional
    Seat reserveSeat(Flight flight, String seatName);
}
