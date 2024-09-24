package com.flightbookings.flight_bookings.services.interfaces;

import com.flightbookings.flight_bookings.models.Booking;

public interface BookingService {
    Booking createBooking(Long flightId, Long passengerId, String seatName);

    Booking updateBooking(Booking updatedBooking);
}
