package com.flightbookings.flight_bookings.services.interfaces;

import com.flightbookings.flight_bookings.models.Flight;

import java.util.List;

public interface SeatService {
    public List<String> initializeSeats(Flight flight, int numRows);
}
