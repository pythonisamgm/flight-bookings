package com.flightbookings.flight_bookings.dtos.DTOBooking;

import com.flightbookings.flight_bookings.dto.BookingDTO;
import com.flightbookings.flight_bookings.models.Booking;

public class BookingConverter {

    public static BookingDTO toDTO (Booking booking) {
        if (booking == null) {
            return null;
        }

        BookingDTO dto = new BookingDTO();
        dto.setBookingId(booking.getBookingId());
        dto.setDateOfBooking(booking.getDateOfBooking());
        dto.setPassengerId(booking.getPassenger() != null ? booking.getPassenger().getPassengerId() : null);
        dto.setFlightId(booking.getFlight() != null ? booking.getFlight().getFlightId() : null);
        dto.setSeatId(booking.getSeat() != null ? booking.getSeat().getSeatId() : null);
        dto.setUserId(booking.getUser() != null ? booking.getUser().getUserId() : null);

        return dto;
    }

    public static Booking toEntity (BookingDTO dto) {
        if (dto == null) {
            return null;
        }

        Booking booking = new Booking();
        booking.setBookingId(dto.getBookingId());
        booking.setDateOfBooking(dto.getDateOfBooking());
        return booking;
    }
}
