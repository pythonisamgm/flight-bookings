package com.flightbookings.flight_bookings.controllers;

import com.flightbookings.flight_bookings.exceptions.FlightNotFoundException;
import com.flightbookings.flight_bookings.models.FlightEntity;
import com.flightbookings.flight_bookings.models.EFlightAirplane;
import com.flightbookings.flight_bookings.services.interfaces.FlightService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
/**
 * Controller for managing flight-related operations such as creating, updating, retrieving, and deleting flights.
 */
@CrossOrigin("*")
@RestController
@RequestMapping("api/v1/flight")
@Tag(name = "Flight", description = "Operations pertaining to flight management")
@RequiredArgsConstructor
public class FlightController {

    private final FlightService flightService;

    /**
     * Creates a new flight.
     *
     * @param flight the flight object to be created.
     * @return the created flight.
     */
    @Operation(summary =  "Create a new flight")
    @PostMapping(value="/create",consumes = "application/json")
    public ResponseEntity<FlightEntity> createFlight(@RequestBody FlightEntity flight) {
        FlightEntity newFlight = flightService.createFlight(flight);
        return new ResponseEntity<>(newFlight, HttpStatus.CREATED);
    }
    /**
     * Updates the availability of all flights based on their current status (e.g., past departure or no available seats).
     *
     * @return a response indicating whether the availability was successfully updated.
     */
    @PutMapping("/updateAvailability")
    public ResponseEntity<String> updateAvailability() {
        flightService.updateFlightAvailability();
        return ResponseEntity.ok("Flight availability updated successfully.");
    }
    /**
     * Retrieves a list of flights by airplane type.
     *
     * @param airplaneType the type of airplane to filter flights.
     * @return a list of flights with the specified airplane type.
     */
    @GetMapping("/byAirplaneType")
    public ResponseEntity<List<FlightEntity>> getFlightsByAirplaneType(@RequestParam EFlightAirplane airplaneType) {
        List<FlightEntity> flights = flightService.getFlightsByAirplaneType(airplaneType);
        return new ResponseEntity<>(flights, HttpStatus.OK);
    }
    /**
     * Retrieves a flight by its ID.
     *
     * @param id the ID of the flight.
     * @return the flight if found, otherwise 404.
     */
    @Operation(summary =  "Get flight by ID")
    @GetMapping("/{id}")
    public ResponseEntity<FlightEntity> getFlightById(@Parameter(description = "ID of the flight to be retrieved") @PathVariable Long id) {
        FlightEntity flight = flightService.getFlightById(id);
        return flight != null ? new ResponseEntity<>(flight, HttpStatus.OK) : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
    /**
     * Retrieves all flights.
     *
     * @return the list of all flights.
     */
    @Operation(summary =  "Get all flights")
    @GetMapping("/")
    public ResponseEntity<List<FlightEntity>> getAllFlights() {
        List<FlightEntity> flights = flightService.getAllFlights();
        return new ResponseEntity<>(flights, HttpStatus.OK);
    }
    /**
     * Updates an existing flight by its ID.
     *
     * @param id the ID of the flight.
     * @param flightDetails the flight details to update.
     * @return the updated flight.
     */
    @Operation(summary = "Update an existing flight")
    @PutMapping("/update/{id}")
    public ResponseEntity<FlightEntity> updateFlight(@Parameter(description = "ID of the flight to be updated") @PathVariable Long id, @RequestBody FlightEntity flightDetails) {
        flightDetails.setFlightId(id);
        try {
            FlightEntity updatedFlight = flightService.updateFlight(flightDetails);
            return new ResponseEntity<>(updatedFlight, HttpStatus.OK);
        } catch (FlightNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
    /**
     * Deletes a flight by its ID.
     *
     * @param id the ID of the flight.
     * @return a 204 response if deleted, otherwise 404.
     */
    @Operation(summary =  "Delete a flight by ID")
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteFlight(@Parameter(description = "ID of the flight to be retrieved") @PathVariable Long id) {
        boolean isDeleted = flightService.deleteFlight(id);
        return isDeleted ? new ResponseEntity<>(HttpStatus.NO_CONTENT) : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}
