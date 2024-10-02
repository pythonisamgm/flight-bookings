package com.flightbookings.flight_bookings.dtos.DTOBooking;

import com.flightbookings.flight_bookings.dto.BookingDTO;
import com.flightbookings.flight_bookings.models.Booking;
import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;

public class BookingConverter {

    private final ModelMapper modelMapper;

    public BookingConverter(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
        configure();
    }

    private void configure() {
        modelMapper.addMappings(new PropertyMap<Booking, BookingDTO>() {
            @Override
            protected void configure() {
                map().setPassengerId(source.getPassenger() != null ? source.getPassenger().getPassengerId() : null);
                map().setFlightId(source.getFlight() != null ? source.getFlight().getFlightId() : null);
                map().setSeatId(source.getSeat() != null ? source.getSeat().getSeatId() : null);
                map().setUserId(source.getUser() != null ? source.getUser().getUserId() : null);
            }
        });
    }

    public BookingDTO convertToDto(Booking booking) {
        return modelMapper.map(booking, BookingDTO.class);
    }

    public Booking convertToEntity(BookingDTO bookingDTO) {
        return modelMapper.map(bookingDTO, Booking.class);
    }
}
