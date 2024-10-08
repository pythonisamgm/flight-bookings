package com.flightbookings.flight_bookings.controllers;

import com.flightbookings.flight_bookings.dtos.DTOAirport.AirportConverter;
import com.flightbookings.flight_bookings.dtos.DTOAirport.AirportDTO;
import com.flightbookings.flight_bookings.models.Airport;
import com.flightbookings.flight_bookings.services.interfaces.AirportService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Controller for managing airports, including retrieving and creating airport records.
 */
@RestController
@RequestMapping("/api/airports")
public class AirportController {
    private final AirportService airportService;
    private final AirportConverter airportConverter;

    /**
     * Constructor to initialize the AirportController with AirportService and AirportConverter.
     *
     * @param airportService  the service for managing airport records.
     * @param airportConverter the converter for Airport and AirportDTO.
     */
    public AirportController(AirportService airportService, AirportConverter airportConverter) {
        this.airportService = airportService;
        this.airportConverter = airportConverter;
    }

    /**
     * Retrieves a list of all airports.
     *
     * This endpoint allows users to fetch all airport records in the system.
     *
     * @return ResponseEntity containing a list of AirportDTOs and an HTTP status of 200 (OK).
     */
    @GetMapping
    public ResponseEntity<List<AirportDTO>> getAllAirports() {
        List<Airport> airports = airportService.getAllAirports();
        List<AirportDTO> airportDTOs = airportConverter.airportsToDtoList(airports);
        return new ResponseEntity<>(airportDTOs, HttpStatus.OK);
    }

    /**
     * Creates a new airport record.
     *
     * This endpoint allows users to create a new airport by providing details in an AirportDTO.
     *
     * @param airportDTO the AirportDTO containing the details of the airport to be created.
     * @return ResponseEntity containing the created AirportDTO and an HTTP status of 201 (Created).
     */
    @PostMapping
    public ResponseEntity<AirportDTO> createAirport(@RequestBody AirportDTO airportDTO) {
        Airport airport = airportConverter.dtoToAirport(airportDTO);
        Airport createdAirport = airportService.createAirport(airport);
        AirportDTO createdAirportDTO = airportConverter.airportToDto(createdAirport);
        return new ResponseEntity<>(createdAirportDTO, HttpStatus.CREATED);
    }
}
