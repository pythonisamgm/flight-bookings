package com.flightbookings.flight_bookings.services.interfaces;

import com.flightbookings.flight_bookings.dtos.DTOSeat.SeatDTO;
import com.flightbookings.flight_bookings.models.Flight;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface SeatService {

    @Transactional
    List<String> initializeSeats(Flight flight, int numRows);

    @Transactional
    SeatDTO reserveSeat(Flight flight, String seatName);
}
