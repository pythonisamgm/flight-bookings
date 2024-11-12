package com.flightbookings.flight_bookings.dtos.DTOBooking;

import com.flightbookings.flight_bookings.models.BookingEntity;
import org.modelmapper.ModelMapper;

public class BookingConverter {

    private final ModelMapper modelMapper;

    public BookingConverter(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    public BookingDTO convertToDto(BookingEntity booking) {
        return modelMapper.map(booking, BookingDTO.class);
    }

    public BookingEntity convertToEntity(BookingDTO bookingDTO) {
        return modelMapper.map(bookingDTO, BookingEntity.class);
    }
}
