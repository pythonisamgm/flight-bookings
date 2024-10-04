package com.flightbookings.flight_bookings.dtos.DTOFlight;

import com.flightbookings.flight_bookings.models.Flight;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class FlightConverter {

    private final ModelMapper modelMapper;

    public FlightConverter(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    public Flight dtoToFlight(FlightDTO flightDTO) {
        return modelMapper.map(flightDTO, Flight.class);
    }

    public FlightDTO flightToDto(Flight flight) {
        return modelMapper.map(flight, FlightDTO.class);
    }

    public List<FlightDTO> flightsToDtoList(List<Flight> flights) {
        List<FlightDTO> flightDTOs = new ArrayList<>();
        for (Flight flight : flights) {
            flightDTOs.add(flightToDto(flight));
        }
        return flightDTOs;
    }

    public List<Flight> dtoListToFlights(List<FlightDTO> flightDTOs) {
        List<Flight> flights = new ArrayList<>();
        for (FlightDTO flightDTO : flightDTOs) {
            flights.add(dtoToFlight(flightDTO));
        }
        return flights;
    }
}
