package com.flightbookings.flight_bookings.dtos.DTOPassenger;

import com.flightbookings.flight_bookings.models.Passenger;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Converter class for converting between Passenger and PassengerDTO objects.
 * This class utilizes ModelMapper for object mapping.
 */
@Component
public class PassengerConverter {

    private final ModelMapper modelMapper;

    /**
     * Constructs a PassengerConverter with the specified ModelMapper.
     *
     * @param modelMapper the ModelMapper used for object mapping
     */
    public PassengerConverter(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    /**
     * Converts a PassengerDTO to a Passenger entity.
     *
     * @param passengerDTO the PassengerDTO to convert
     * @return the converted Passenger entity
     */
    public Passenger dtoToPassenger(PassengerDTO passengerDTO) {
        return modelMapper.map(passengerDTO, Passenger.class);
    }

    /**
     * Converts a Passenger entity to a PassengerDTO.
     *
     * @param passenger the Passenger entity to convert
     * @return the converted PassengerDTO
     */
    public PassengerDTO passengerToDto(Passenger passenger) {
        return modelMapper.map(passenger, PassengerDTO.class);
    }

    /**
     * Converts a list of Passenger entities to a list of PassengerDTOs.
     *
     * @param passengers the list of Passenger entities to convert
     * @return the list of converted PassengerDTOs
     */
    public List<PassengerDTO> passengersToDtoList(List<Passenger> passengers) {
        return passengers.stream()
                .map(this::passengerToDto)
                .collect(Collectors.toList());
    }

    /**
     * Converts a list of PassengerDTOs to a list of Passenger entities.
     *
     * @param passengerDTOs the list of PassengerDTOs to convert
     * @return the list of converted Passenger entities
     */
    public List<Passenger> dtoListToPassengers(List<PassengerDTO> passengerDTOs) {
        return passengerDTOs.stream()
                .map(this::dtoToPassenger)
                .collect(Collectors.toList());
    }
}
