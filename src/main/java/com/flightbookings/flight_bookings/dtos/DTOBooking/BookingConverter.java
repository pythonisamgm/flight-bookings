package com.flightbookings.flight_bookings.dtos.DTOBooking;

import com.flightbookings.flight_bookings.models.Booking;
import org.modelmapper.ModelMapper;

public class BookingConverter {

    private final ModelMapper modelMapper;

    public BookingConverter(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    public BookingDTO convertToDto(Booking booking) {
        return modelMapper.map(booking, BookingDTO.class);
    }

    public Booking convertToEntity(BookingDTO bookingDTO) {
        return modelMapper.map(bookingDTO, Booking.class);
    }
}
