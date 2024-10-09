package com.flightbookings.flight_bookings.dtos.DTOAirport;

import com.flightbookings.flight_bookings.models.Airport;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Converter class for converting between Airport and AirportDTO objects.
 * This class uses ModelMapper for object mapping.
 */
@Component
public class AirportConverter {

    private final ModelMapper modelMapper;

    /**
     * Constructs an AirportConverter with the specified ModelMapper.
     *
     * @param modelMapper the ModelMapper used for object mapping
     */
    public AirportConverter(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    /**
     * Converts an AirportDTO to an Airport entity.
     *
     * @param airportDTO the AirportDTO to convert
     * @return the converted Airport entity
     */
    public Airport dtoToAirport(AirportDTO airportDTO) {
        return modelMapper.map(airportDTO, Airport.class);
    }

    /**
     * Converts an Airport entity to an AirportDTO.
     *
     * @param airport the Airport entity to convert
     * @return the converted AirportDTO
     */
    public AirportDTO airportToDto(Airport airport) {
        return modelMapper.map(airport, AirportDTO.class);
    }

    /**
     * Converts a list of Airport entities to a list of AirportDTOs.
     *
     * @param airports the list of Airport entities to convert
     * @return the list of converted AirportDTOs
     */
    public List<AirportDTO> airportsToDtoList(List<Airport> airports) {
        return airports.stream()
                .map(this::airportToDto)
                .collect(Collectors.toList());
    }

    /**
     * Converts a list of AirportDTOs to a list of Airport entities.
     *
     * @param airportDTOs the list of AirportDTOs to convert
     * @return the list of converted Airport entities
     */
    public List<Airport> dtoListToAirports(List<AirportDTO> airportDTOs) {
        return airportDTOs.stream()
                .map(this::dtoToAirport)
                .collect(Collectors.toList());
    }
}
