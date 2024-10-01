package com.flightbookings.flight_bookings.controllers;

import com.flightbookings.flight_bookings.models.Airport;
import com.flightbookings.flight_bookings.services.interfaces.AirportService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@CrossOrigin("*")
@RestController
@RequestMapping("api/v1/airport")
@Tag(name = "Airport Management", description = "Operations pertaining to airport management")
public class AirportController {

    private final AirportService airportService;

    public AirportController(AirportService airportService) {
        this.airportService = airportService;
    }
    @Operation(summary = "Create a new airport", description = "Create a new airport with the provided details")
    @PostMapping
    public ResponseEntity<Airport> createAirport(@RequestBody Airport airport) {
        Airport newAirport = airportService.createAirport(airport);
        return ResponseEntity.ok(newAirport);
    }
    @Operation(summary = "Get airport by ID", description = "Retrieve an airport by its ID")
    @GetMapping("/{id}")
    public ResponseEntity<Airport> getAirportById(@PathVariable Long id) {
        Optional<Airport> airport = airportService.getAirportById(id);
        return airport.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
    @Operation(summary = "Get all airports", description = "Retrieve a list of all airports")
    @GetMapping
    public ResponseEntity<List<Airport>> getAllAirports() {
        List<Airport> airports = airportService.getAllAirports();
        return ResponseEntity.ok(airports);
    }
    @Operation(summary = "Update an existing airport", description = "Update the details of an existing airport by its ID")
    @PutMapping("/{id}")
    public ResponseEntity<Airport> updateAirport(@RequestBody Airport airport, @PathVariable Long id) {
        Airport updatedAirport = airportService.updateAirport(airport, id);
        return ResponseEntity.ok(updatedAirport);
    }

}
