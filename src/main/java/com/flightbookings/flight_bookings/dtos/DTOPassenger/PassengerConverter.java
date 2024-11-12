package com.flightbookings.flight_bookings.dtos.DTOPassenger;

import com.flightbookings.flight_bookings.models.PassengerEntity;
import org.modelmapper.ModelMapper;

public class PassengerConverter {

    private final ModelMapper modelMapper;

    public PassengerConverter(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    public PassengerDTO convertToDto(PassengerEntity passenger) {
        return modelMapper.map(passenger, PassengerDTO.class);
    }

    public PassengerEntity convertToEntity(PassengerDTO passengerDTO) {
        return modelMapper.map(passengerDTO, PassengerEntity.class);
    }
}
