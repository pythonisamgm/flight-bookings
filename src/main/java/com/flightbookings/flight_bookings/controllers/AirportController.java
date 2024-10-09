package com.flightbookings.flight_bookings.controllers;

import com.flightbookings.flight_bookings.models.Airport;
import com.flightbookings.flight_bookings.services.interfaces.AirportService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Controller for managing airports, including retrieving and creating airports.
 */
@CrossOrigin("*")
@RestController
@RequestMapping("/api/airports")
@Tag(name = "Airport Management", description = "Operations pertaining to airport management")
public class AirportController {
    private final AirportService airportService;

    /**
     * Constructor to initialize AirportController with AirportService.
     *
     * @param airportService the service for airport management.
     */
    public AirportController(AirportService airportService) {
        this.airportService = airportService;
    }

    /**
     * Retrieves all airports.
     *
     * @return a list of all airports.
     */
    @Operation(summary = "Get all airports")
    @GetMapping
    public ResponseEntity<List<Airport>> getAllAirports() {
        List<Airport> airports = airportService.getAllAirports();
        return new ResponseEntity<>(airports, HttpStatus.OK);
    }

    /**
     * Creates a new airport.
     *
     * @param airport the airport object to be created.
     * @return the created airport.
     */
    @Operation(summary = "Create a new airport")
    @PostMapping
    public ResponseEntity<Airport> createAirport(@RequestBody Airport airport) {
        Airport createdAirport = airportService.createAirport(airport);
        return new ResponseEntity<>(createdAirport, HttpStatus.CREATED);
    }
}
