package com.flightbookings.flight_bookings.dtos.DTOSeat;

import com.flightbookings.flight_bookings.models.Seat;
import org.modelmapper.ModelMapper;

public class SeatConverter {

    private final ModelMapper modelMapper;

    public SeatConverter(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    public SeatDTO convertToDto(Seat seat) {
        return modelMapper.map(seat, SeatDTO.class);
    }

    public Seat convertToEntity(SeatDTO seatDTO) {
        return modelMapper.map(seatDTO, Seat.class);
    }
}
