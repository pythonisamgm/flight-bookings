package com.flightbookings.flight_bookings.services.interfaces;

import com.flightbookings.flight_bookings.models.Flight;

public interface SeatService {
    void initializeSeats(Flight flight, int numRows);
}
