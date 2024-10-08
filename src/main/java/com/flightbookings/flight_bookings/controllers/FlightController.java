package com.flightbookings.flight_bookings.controllers;

import com.flightbookings.flight_bookings.exceptions.FlightNotFoundException;
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
/**
 * Controller for managing flight-related operations such as creating, updating, retrieving, and deleting flights.
 */
@CrossOrigin("*")
@RestController
@RequestMapping("api/v1/flight")
@Tag(name = "Flight", description = "Operations pertaining to flight management")
public class FlightController {

    private final FlightService flightService;
    /**
     * Constructor to initialize the FlightController with a FlightService.
     *
     * @param flightService the flight service for managing flight operations.
     */
    public FlightController(FlightService flightService) {
        this.flightService = flightService;
    }
    /**
     * Creates a new flight.
     *
     * @param flight the flight object to be created.
     * @return the created flight.
     */
    @Operation(summary =  "Create a new flight")
    @PostMapping(value="/create",consumes = "application/json")
    public ResponseEntity<Flight> createFlight(@RequestBody Flight flight) {
        Flight newFlight = flightService.createFlight(flight);
        return new ResponseEntity<>(newFlight, HttpStatus.CREATED);
    }
    /**
     * Retrieves a flight by its ID.
     *
     * @param id the ID of the flight.
     * @return the flight if found, otherwise 404.
     */
    @Operation(summary =  "Get flight by ID")
    @GetMapping("/{id}")
    public ResponseEntity<Flight> getFlightById(@Parameter(description = "ID of the flight to be retrieved") @PathVariable Long id) {
        Flight flight = flightService.getFlightById(id);
        return flight != null ? new ResponseEntity<>(flight, HttpStatus.OK) : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
    /**
     * Retrieves all flights.
     *
     * @return the list of all flights.
     */
    @Operation(summary =  "Get all flights")
    @GetMapping("/")
    public ResponseEntity<List<Flight>> getAllFlights() {
        List<Flight> flights = flightService.getAllFlights();
        return new ResponseEntity<>(flights, HttpStatus.OK);
    }
    /**
     * Updates an existing flight by its ID.
     *
     * @param id the ID of the flight.
     * @param flightDetails the flight details to update.
     * @return the updated flight.
     */
    @Operation(summary =  "Update an existing flight")
    @PutMapping("/update/{id}")
    public ResponseEntity<Flight> updateFlight(@Parameter(description = "ID of the flight to be retrieved") @PathVariable Long id, @RequestBody Flight flightDetails) {
        Flight updatedFlight = flightService.updateFlight(id, flightDetails);
        return updatedFlight != null ? new ResponseEntity<>(updatedFlight, HttpStatus.OK) : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
    /**
     * Deletes a flight by its ID.
     *
     * @param id the ID of the flight.
     * @return a 204 response if deleted, otherwise 404.
     */
    @Operation(summary = "Delete a flight by ID")
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteFlight(@Parameter(description = "ID of the flight to be deleted") @PathVariable Long id) {
        try {
            flightService.deleteFlight(id);
            return ResponseEntity.noContent().build();
        } catch (FlightNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

//    @GetMapping("/search")
//    public ResponseEntity<List<Flight>> searchFlightsByCity(@RequestParam String city) {
//        List<Flight> flights = flightService.searchFlightsByCity(city);
//        return new ResponseEntity<>(flights, HttpStatus.OK);
//    }
    /**
     * Cancels a flight by its ID.
     *
     * @param id the ID of the flight to be canceled.
     * @return a response indicating whether the cancellation was successful.
     */
    @DeleteMapping("/{id}/cancel")
    public ResponseEntity<String> cancelFlight(@PathVariable Long id) {
        flightService.cancelFlight(id);
        return ResponseEntity.ok("Flight canceled successfully.");
    }
    /**
     * Delays a flight by setting a new departure time.
     *
     * @param id              the ID of the flight to be delayed.
     * @param newDepartureTime the new departure time for the flight in ISO-8601 format.
     * @return a response indicating whether the delay was successful.
     */
    @PostMapping("/{id}/delay")
    public ResponseEntity<String> delayFlight(@PathVariable Long id, @RequestParam String newDepartureTime) {
        LocalDateTime departureTime = LocalDateTime.parse(newDepartureTime);
        flightService.delayFlight(id, departureTime);
        return ResponseEntity.ok("Flight delayed successfully.");
    }
    /**
     * Updates the availability of all flights based on their current status (e.g., past departure or no available seats).
     *
     * @return a response indicating whether the availability was successfully updated.
     */
    @PostMapping("/updateAvailability")
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
    public ResponseEntity<List<Flight>> getFlightsByAirplaneType(@RequestParam EFlightAirplane airplaneType) {
        List<Flight> flights = flightService.getFlightsByAirplaneType(airplaneType);
        return new ResponseEntity<>(flights, HttpStatus.OK);
    }
}
