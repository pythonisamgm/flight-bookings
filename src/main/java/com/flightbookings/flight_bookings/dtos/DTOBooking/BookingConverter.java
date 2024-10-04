package com.flightbookings.flight_bookings.dtos.DTOBooking;

import com.flightbookings.flight_bookings.models.Booking;
import com.flightbookings.flight_bookings.models.Flight;
import com.flightbookings.flight_bookings.services.interfaces.FlightService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class BookingConverter {

    private final ModelMapper modelMapper;
    private final FlightService flightService;

    public BookingConverter(ModelMapper modelMapper, FlightService flightService) {
        this.modelMapper = modelMapper;
        this.flightService = flightService;
    }

    public Booking dtoToBooking(BookingDTO bookingDTO) {
        Booking booking = modelMapper.map(bookingDTO, Booking.class);
        if (bookingDTO.getFlightId() != null) {
            Flight flight = flightService.getFlightById(bookingDTO.getFlightId());
            booking.setFlight(flight);
        }
        return booking;
    }

    public BookingDTO bookingToDto(Booking booking) {
        BookingDTO bookingDTO = modelMapper.map(booking, BookingDTO.class);
        if (booking.getFlight() != null) {
            bookingDTO.setFlightId(booking.getFlight().getFlightId());
        }
        return bookingDTO;
    }

    public List<BookingDTO> bookingsToDtoList(List<Booking> bookings) {
        return bookings.stream()
                .map(this::bookingToDto)
                .collect(Collectors.toList());
    }

    public List<Booking> dtoListToBookings(List<BookingDTO> bookingDTOs) {
        return bookingDTOs.stream()
                .map(this::dtoToBooking)
                .collect(Collectors.toList());
    }
}
