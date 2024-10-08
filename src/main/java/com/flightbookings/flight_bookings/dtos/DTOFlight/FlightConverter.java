package com.flightbookings.flight_bookings.dtos.DTOFlight;

import com.flightbookings.flight_bookings.models.Flight;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class FlightConverter {

    private final ModelMapper modelMapper;

    public FlightConverter(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    /**
     * Converts a FlightDTO to a Flight entity.
     *
     * @param flightDTO The FlightDTO to convert.
     * @return The Flight entity.
     */
    public Flight dtoToFlight(FlightDTO flightDTO) {
        return modelMapper.map(flightDTO, Flight.class);
    }

    /**
     * Converts a Flight entity to a FlightDTO.
     *
     * @param flight The Flight entity to convert.
     * @return The FlightDTO.
     */
    public FlightDTO flightToDto(Flight flight) {
        return modelMapper.map(flight, FlightDTO.class);
    }

    /**
     * Converts a list of Flight entities to a list of FlightDTOs.
     *
     * @param flights The list of Flight entities to convert.
     * @return A list of FlightDTOs.
     */
    public List<FlightDTO> flightsToDtoList(List<Flight> flights) {
        return flights.stream()
                .map(this::flightToDto)
                .collect(Collectors.toList());
    }

    /**
     * Converts a list of FlightDTOs to a list of Flight entities.
     *
     * @param flightDTOs The list of FlightDTOs to convert.
     * @return A list of Flight entities.
     */
    public List<Flight> dtoListToFlights(List<FlightDTO> flightDTOs) {
        return flightDTOs.stream()
                .map(this::dtoToFlight)
                .collect(Collectors.toList());
    }
}
