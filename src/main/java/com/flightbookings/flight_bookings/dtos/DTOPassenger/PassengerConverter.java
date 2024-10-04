package com.flightbookings.flight_bookings.dtos.DTOPassenger;

import com.flightbookings.flight_bookings.models.Passenger;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class PassengerConverter {

    private final ModelMapper modelMapper;

    public PassengerConverter(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    public Passenger dtoToPassenger(PassengerDTO passengerDTO) {
        return modelMapper.map(passengerDTO, Passenger.class);
    }

    public PassengerDTO passengerToDto(Passenger passenger) {
        return modelMapper.map(passenger, PassengerDTO.class);
    }

    public List<PassengerDTO> passengersToDtoList(List<Passenger> passengers) {
        return passengers.stream()
                .map(this::passengerToDto)
                .collect(Collectors.toList());
    }

    public List<Passenger> dtoListToPassengers(List<PassengerDTO> passengerDTOs) {
        return passengerDTOs.stream()
                .map(this::dtoToPassenger)
                .collect(Collectors.toList());
    }
}
