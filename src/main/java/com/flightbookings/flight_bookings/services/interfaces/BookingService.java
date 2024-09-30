package com.flightbookings.flight_bookings.services.interfaces;

import com.flightbookings.flight_bookings.models.Booking;
import com.flightbookings.flight_bookings.models.User;

import java.util.List;

public interface BookingService {

    Booking createBooking(Long flightId, Long passengerId, String seatName, User user);

    Booking updateBooking(Booking updatedBooking);

    Booking createBooking2(Booking booking);

    Booking getBookingById(Long id, User user);

    List<Booking> getAllBookings(User user);

    Booking updateBooking2(Long id, Booking bookingDetails);

    boolean deleteBooking(Long id);
}
