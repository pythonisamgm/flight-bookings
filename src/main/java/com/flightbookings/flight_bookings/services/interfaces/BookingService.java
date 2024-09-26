package com.flightbookings.flight_bookings.services.interfaces;

import com.flightbookings.flight_bookings.models.Booking;

import java.util.List;

public interface BookingService {

    Booking createBooking(Long flightId, Long passengerId, String seatName);

    Booking updateBooking(Booking updatedBooking);

    Booking createBooking2(Booking booking);

    Booking getBookingById(Long id);

    List<Booking> getAllBookings();

    Booking updateBooking2(Long id, Booking bookingDetails);

    boolean deleteBooking(Long id);
}
