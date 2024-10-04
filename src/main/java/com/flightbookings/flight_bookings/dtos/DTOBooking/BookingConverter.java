package com.flightbookings.flight_bookings.dtos.DTOBooking;

import com.flightbookings.flight_bookings.models.Booking;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class BookingConverter {

    private final ModelMapper modelMapper;

    public BookingConverter(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    public Booking dtoToBooking(BookingDTO bookingDTO) {
        return modelMapper.map(bookingDTO, Booking.class);
    }

    public BookingDTO bookingToDto(Booking booking) {
        return modelMapper.map(booking, BookingDTO.class);
    }

    public List<BookingDTO> bookingsToDtoList(List<Booking> bookings) {
        List<BookingDTO> bookingDTOs = new ArrayList<>();
        for (Booking booking : bookings) {
            bookingDTOs.add(bookingToDto(booking));
        }
        return bookingDTOs;
    }

    public List<Booking> dtoListToBookings(List<BookingDTO> bookingDTOs) {
        List<Booking> bookings = new ArrayList<>();
        for (BookingDTO bookingDTO : bookingDTOs) {
            bookings.add(dtoToBooking(bookingDTO));
        }
        return bookings;
    }
}
