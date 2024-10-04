package com.flightbookings.flight_bookings.dtos.DTOPassenger;

import com.flightbookings.flight_bookings.models.Passenger;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

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
        List<PassengerDTO> passengerDTOs = new ArrayList<>();
        for (Passenger passenger : passengers) {
            passengerDTOs.add(passengerToDto(passenger));
        }
        return passengerDTOs;
    }

    public List<Passenger> dtoListToPassengers(List<PassengerDTO> passengerDTOs) {
        List<Passenger> passengers = new ArrayList<>();
        for (PassengerDTO passengerDTO : passengerDTOs) {
            passengers.add(dtoToPassenger(passengerDTO));
        }
        return passengers;
    }
}
