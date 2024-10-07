package com.flightbookings.flight_bookings.dtos.DTOAirport;

import com.flightbookings.flight_bookings.models.Airport;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class AirportConverter {

    private final ModelMapper modelMapper;

    public AirportConverter(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    public Airport dtoToAirport(AirportDTO airportDTO) {
        return modelMapper.map(airportDTO, Airport.class);
    }

    public AirportDTO airportToDto(Airport airport) {
        return modelMapper.map(airport, AirportDTO.class);
    }

    public List<AirportDTO> airportsToDtoList(List<Airport> airports) {
        return airports.stream()
                .map(this::airportToDto)
                .collect(Collectors.toList());
    }

    public List<Airport> dtoListToAirports(List<AirportDTO> airportDTOs) {
        return airportDTOs.stream()
                .map(this::dtoToAirport)
                .collect(Collectors.toList());
    }
}
