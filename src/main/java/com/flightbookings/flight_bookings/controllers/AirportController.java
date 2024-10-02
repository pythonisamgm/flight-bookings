package com.flightbookings.flight_bookings.controllers;

import com.flightbookings.flight_bookings.models.Airport;
import com.flightbookings.flight_bookings.services.interfaces.AirportService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
/**
 * Controller for managing airport-related operations such as creating, retrieving, updating, and deleting airports.
 */
@CrossOrigin("*")
@RestController
@RequestMapping("api/v1/airport")
@Tag(name = "Airport Management", description = "Operations pertaining to airport management")
public class AirportController {

    private final AirportService airportService;
    /**
     * Constructor to initialize the AirportController with an AirportService.
     *
     * @param airportService the airport service for managing airport operations.
     */
    public AirportController(AirportService airportService) {
        this.airportService = airportService;
    }
    /**
     * Creates a new airport.
     *
     * @param airport the airport object to be created.
     * @return the created airport.
     */
    @Operation(summary = "Create a new airport", description = "Create a new airport with the provided details")
    @PostMapping
    public ResponseEntity<Airport> createAirport(@RequestBody Airport airport) {
        Airport newAirport = airportService.createAirport(airport);
        return ResponseEntity.ok(newAirport);
    }
    /**
     * Retrieves an airport by its ID.
     *
     * @param id the ID of the airport.
     * @return the airport if found, otherwise a 404 response.
     */
    @Operation(summary = "Get airport by ID", description = "Retrieve an airport by its ID")
    @GetMapping("/{id}")
    public ResponseEntity<Airport> getAirportById(@PathVariable Long id) {
        Optional<Airport> airport = airportService.getAirportById(id);
        return airport.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
    /**
     * Retrieves a list of all airports.
     *
     * @return the list of airports.
     */
    @Operation(summary = "Get all airports", description = "Retrieve a list of all airports")
    @GetMapping
    public ResponseEntity<List<Airport>> getAllAirports() {
        List<Airport> airports = airportService.getAllAirports();
        return ResponseEntity.ok(airports);
    }
    /**
     * Updates an existing airport by its ID.
     *
     * @param airport the airport object with updated details.
     * @param id      the ID of the airport to be updated.
     * @return the updated airport.
     */
    @Operation(summary = "Update an existing airport", description = "Update the details of an existing airport by its ID")
    @PutMapping("/{id}")
    public ResponseEntity<Airport> updateAirport(@RequestBody Airport airport, @PathVariable Long id) {
        Airport updatedAirport = airportService.updateAirport(airport, id);
        return ResponseEntity.ok(updatedAirport);
    }

}
