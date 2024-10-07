package com.flightbookings.flight_bookings.controllers;

import com.flightbookings.flight_bookings.dtos.DTOAirport.AirportConverter;
import com.flightbookings.flight_bookings.dtos.DTOAirport.AirportDTO;
import com.flightbookings.flight_bookings.models.Airport;
import com.flightbookings.flight_bookings.services.interfaces.AirportService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/airports")
public class AirportController {
    private final AirportService airportService;
    private final AirportConverter airportConverter;

    public AirportController(AirportService airportService, AirportConverter airportConverter) {
        this.airportService = airportService;
        this.airportConverter = airportConverter;
    }

    @GetMapping
    public ResponseEntity<List<AirportDTO>> getAllAirports() {
        List<Airport> airports = airportService.getAllAirports();
        List<AirportDTO> airportDTOs = airportConverter.airportsToDtoList(airports);
        return new ResponseEntity<>(airportDTOs, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<AirportDTO> createAirport(@RequestBody AirportDTO airportDTO) {
        Airport airport = airportConverter.dtoToAirport(airportDTO);
        Airport createdAirport = airportService.createAirport(airport);
        AirportDTO createdAirportDTO = airportConverter.airportToDto(createdAirport);
        return new ResponseEntity<>(createdAirportDTO, HttpStatus.CREATED);
    }
}
