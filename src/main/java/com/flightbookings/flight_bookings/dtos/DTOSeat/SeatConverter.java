package com.flightbookings.flight_bookings.dtos.DTOSeat;

import com.flightbookings.flight_bookings.models.SeatEntity;
import org.modelmapper.ModelMapper;

public class SeatConverter {

    private final ModelMapper modelMapper;

    public SeatConverter(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    public SeatDTO convertToDto(SeatEntity seat) {
        return modelMapper.map(seat, SeatDTO.class);
    }

    public SeatEntity convertToEntity(SeatDTO seatDTO) {
        return modelMapper.map(seatDTO, SeatEntity.class);
    }
}
