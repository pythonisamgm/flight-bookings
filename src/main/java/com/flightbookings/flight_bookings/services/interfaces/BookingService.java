package com.flightbookings.flight_bookings.services.interfaces;

import com.flightbookings.flight_bookings.dtos.DTOBooking.BookingDTO;
import com.flightbookings.flight_bookings.dtos.DTOUser.UserDTO;
import com.flightbookings.flight_bookings.models.Booking;
import com.flightbookings.flight_bookings.models.User;

import java.util.List;

public interface BookingService {

    BookingDTO createBooking(Long flightId, Long passengerId, String seatName, Long userId);

    BookingDTO updateBooking(BookingDTO updatedBookingDTO);

    BookingDTO getBookingById(Long id, User user);

    List<BookingDTO> getAllBookingsByUser(User user);

    List<BookingDTO> getAllBookings();

    boolean deleteBooking(Long id);
}
