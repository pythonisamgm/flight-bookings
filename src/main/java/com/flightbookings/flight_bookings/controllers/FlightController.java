package com.flightbookings.flight_bookings.controllers;

import com.flightbookings.flight_bookings.models.Flight;
import com.flightbookings.flight_bookings.models.EFlightAirplane;
import com.flightbookings.flight_bookings.services.interfaces.FlightService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@CrossOrigin("*")
@RestController
@RequestMapping("api/v1/flights")
@Tag(name = "Flight", description = "Operations pertaining to flight management")
public class FlightController {

    private final FlightService flightService;

    public FlightController(FlightService flightService) {
        this.flightService = flightService;
    }

    @Operation(summary = "Create a new flight")
    @PostMapping(consumes = "application/json")
    public ResponseEntity<Flight> createFlight(@RequestBody Flight flight) {
        Flight newFlight = flightService.createFlight(flight);
        return new ResponseEntity<>(newFlight, HttpStatus.CREATED);
    }

    @Operation(summary = "Get flight by ID")
    @GetMapping("/{id}")
    public ResponseEntity<Flight> getFlightById(@Parameter(description = "ID of the flight to be retrieved") @PathVariable Long id) {
        Flight flight = flightService.getFlightById(id);
        return flight != null ? new ResponseEntity<>(flight, HttpStatus.OK) : ResponseEntity.notFound().build();
    }

    @Operation(summary = "Get all flights")
    @GetMapping
    public ResponseEntity<List<Flight>> getAllFlights() {
        List<Flight> flights = flightService.getAllFlights();
        return new ResponseEntity<>(flights, HttpStatus.OK);
    }

    @Operation(summary = "Update an existing flight")
    @PutMapping("/{id}")
    public ResponseEntity<Flight> updateFlight(@Parameter(description = "ID of the flight to be updated") @PathVariable Long id, @RequestBody Flight flightDetails) {
        Flight updatedFlight = flightService.updateFlight(id, flightDetails);
        return updatedFlight != null ? new ResponseEntity<>(updatedFlight, HttpStatus.OK) : ResponseEntity.notFound().build();
    }

    @Operation(summary = "Delete a flight by ID")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteFlight(@Parameter(description = "ID of the flight to be deleted") @PathVariable Long id) {
        boolean isDeleted = flightService.deleteFlight(id);
        return isDeleted ? ResponseEntity.noContent().build() : ResponseEntity.notFound().build();
    }

    @Operation(summary = "Cancel a flight")
    @DeleteMapping("/{id}/cancel")
    public ResponseEntity<String> cancelFlight(@Parameter(description = "ID of the flight to be canceled") @PathVariable Long id) {
        flightService.cancelFlight(id);
        return ResponseEntity.ok("Flight canceled successfully.");
    }

    @Operation(summary = "Delay a flight")
    @PostMapping("/{id}/delay")
    public ResponseEntity<String> delayFlight(
            @Parameter(description = "ID of the flight to be delayed") @PathVariable Long id,
            @Parameter(description = "New departure time in ISO format") @RequestParam String newDepartureTime) {
        LocalDateTime departureTime = LocalDateTime.parse(newDepartureTime);
        flightService.delayFlight(id, departureTime);
        return ResponseEntity.ok("Flight delayed successfully.");
    }

    @Operation(summary = "Update flight availability")
    @PostMapping("/updateAvailability")
    public ResponseEntity<String> updateAvailability() {
        flightService.updateFlightAvailability();
        return ResponseEntity.ok("Flight availability updated successfully.");
    }

    @Operation(summary = "Get flights by airplane type")
    @GetMapping("/byAirplaneType")
    public ResponseEntity<List<Flight>> getFlightsByAirplaneType(@Parameter(description = "Type of airplane") @RequestParam EFlightAirplane airplaneType) {
        List<Flight> flights = flightService.getFlightsByAirplaneType(airplaneType);
        return new ResponseEntity<>(flights, HttpStatus.OK);
    }
}
