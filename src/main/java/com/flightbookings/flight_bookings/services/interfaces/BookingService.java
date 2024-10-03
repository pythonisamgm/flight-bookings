package com.flightbookings.flight_bookings.services.interfaces;

import com.flightbookings.flight_bookings.dtos.DTOBooking.BookingDTO;
import com.flightbookings.flight_bookings.dtos.DTOUser.UserDTO;
import com.flightbookings.flight_bookings.models.User;

import java.util.List;

public interface BookingService {

    BookingDTO createBooking(Long flightId, Long passengerId, String seatName, UserDTO user);

    BookingDTO updateBooking(BookingDTO updatedBookingDTO);

    BookingDTO getBookingById(Long userid, UserDTO user);

    List<BookingDTO> getAllBookings(UserDTO user);

    BookingDTO updateBooking2(Long id, BookingDTO bookingDetailsDTO);

    boolean deleteBooking(Long id);
}
