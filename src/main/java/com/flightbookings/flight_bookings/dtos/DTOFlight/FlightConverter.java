package com.flightbookings.flight_bookings.dtos.DTOFlight;

import com.flightbookings.flight_bookings.models.Flight;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
public class FlightConverter {

    private final ModelMapper modelMapper;

    public FlightConverter(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    public Flight dtoToFlight(FlightDTO flightDTO) {
        return Optional.ofNullable(flightDTO)
                .map(dto -> modelMapper.map(dto, Flight.class))
                .orElse(null);
    }

    public FlightDTO flightToDto(Flight flight) {
        return Optional.ofNullable(flight)
                .map(flt -> modelMapper.map(flt, FlightDTO.class))
                .orElse(null);
    }

    public List<FlightDTO> flightsToDtoList(List<Flight> flights) {
        return Optional.ofNullable(flights)
                .map(fltList -> fltList.stream()
                        .map(this::flightToDto)
                        .collect(Collectors.toList()))
                .orElseGet(List::of);
    }

    public List<Flight> dtoListToFlights(List<FlightDTO> flightDTOs) {
        return Optional.ofNullable(flightDTOs)
                .map(dtoList -> dtoList.stream()
                        .map(this::dtoToFlight)
                        .collect(Collectors.toList()))
                .orElseGet(List::of);
    }
}
