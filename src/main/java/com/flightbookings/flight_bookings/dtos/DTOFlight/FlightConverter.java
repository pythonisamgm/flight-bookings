package com.flightbookings.flight_bookings.dtos.DTOFlight;

import com.flightbookings.flight_bookings.models.FlightEntity;
import org.modelmapper.ModelMapper;


public class FlightConverter {

    private final ModelMapper modelMapper;

    public FlightConverter(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    public FlightDTO convertToDto(FlightEntity flight) {
        return modelMapper.map(flight, FlightDTO.class);
    }

    public FlightEntity convertToEntity(FlightDTO flightDTO) {
        return modelMapper.map(flightDTO, FlightEntity.class);
    }
}
