package com.flightbookings.flight_bookings.controllers;

import com.flightbookings.flight_bookings.models.Passenger;
import com.flightbookings.flight_bookings.services.interfaces.PassengerService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
/**
 * Controller for managing passenger-related operations such as creating, updating, retrieving, and deleting passengers.
 */
@CrossOrigin("*")
@RestController
@RequestMapping("/api/v1/passengers")
@Tag(name ="Passenger Management", description = "Operations pertaining to passenger management")
public class PassengerController {

    private final PassengerService passengerService;
    /**
     * Constructor to initialize the PassengerController with a PassengerService.
     *
     * @param passengerService the passenger service for managing passenger operations.
     */
    public PassengerController(PassengerService passengerService) {
        this.passengerService = passengerService;
    }
    /**
     * Creates a new passenger.
     *
     * @param passenger the passenger object to be created.
     * @return the created passenger.
     */
    @Operation(summary = "Create a new passenger")
    @PostMapping(value="/create",consumes = "application/json")
    public ResponseEntity<Passenger> createPassenger(@RequestBody Passenger passenger) {
        Passenger newPassenger = passengerService.createPassenger(passenger);
        return new ResponseEntity<>(newPassenger, HttpStatus.CREATED);
    }
    /**
     * Retrieves a passenger by its ID.
     *
     * @param id the ID of the passenger.
     * @return the passenger if found, otherwise 404.
     */
    @Operation(summary = "Get a passenger by ID")
    @GetMapping("/{id}")
    public ResponseEntity<Passenger> getPassengerById(@Parameter(description = "ID of the booking  to be retrieved") @PathVariable Long id) {
        Passenger passenger = passengerService.getPassengerById(id);
        if (passenger != null) {
            return new ResponseEntity<>(passenger, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
    /**
     * Retrieves all passengers.
     *
     * @return the list of passengers.
     */
    @Operation(summary = "Get all passengers")
    @GetMapping("/")
    public ResponseEntity<List<Passenger>> getAllPassengers() {
        List<Passenger> passengers = passengerService.getAllPassengers();
        return new ResponseEntity<>(passengers, HttpStatus.OK);
    }
    /**
     * Updates a passenger by its ID.
     *
     * @param id              the ID of the passenger to be updated.
     * @param passengerDetails the passenger details to update.
     * @return the updated passenger.
     */
    @Operation(summary = "Update a passenger")
    @PutMapping("/update/{id}")
    public ResponseEntity<Passenger> updatePassenger(@Parameter(description = "ID of the booking  to be retrieved") @PathVariable Long id, @RequestBody Passenger passengerDetails) {
        Passenger updatedPassenger = passengerService.updatePassenger(id, passengerDetails);
        if (updatedPassenger != null) {
            return new ResponseEntity<>(updatedPassenger, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
    /**
     * Deletes a passenger by its ID.
     *
     * @param id the ID of the passenger.
     * @return a 204 response if deleted, otherwise 404.
     */
    @Operation(summary = "Delete a passenger by ID")
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deletePassenger(@Parameter(description = "ID of the booking  to be retrieved") @PathVariable Long id) {
        boolean isDeleted = passengerService.deletePassenger(id);
        if (isDeleted) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
