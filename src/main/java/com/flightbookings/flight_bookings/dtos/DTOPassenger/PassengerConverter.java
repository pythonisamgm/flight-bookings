package com.flightbookings.flight_bookings.dtos.DTOPassenger;

import com.flightbookings.flight_bookings.models.Passenger;
import org.modelmapper.ModelMapper;

public class PassengerConverter {

    private final ModelMapper modelMapper;

    public PassengerConverter(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    public PassengerDTO convertToDto(Passenger passenger) {
        return modelMapper.map(passenger, PassengerDTO.class);
    }

    public Passenger convertToEntity(PassengerDTO passengerDTO) {
        return modelMapper.map(passengerDTO, Passenger.class);
    }
}
