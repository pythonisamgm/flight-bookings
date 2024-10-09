package com.flightbookings.flight_bookings.controllers;

import com.flightbookings.flight_bookings.dtos.DTOPassenger.PassengerDTO;
import com.flightbookings.flight_bookings.dtos.DTOPassenger.PassengerConverter;
import com.flightbookings.flight_bookings.models.Passenger;
import com.flightbookings.flight_bookings.services.interfaces.PassengerService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin("*")
@RestController
@RequestMapping("/api/v1/passengers")
@Tag(name ="Passenger Management", description = "Operations pertaining to passenger management")
public class PassengerController {

    private final PassengerService passengerService;
    private final PassengerConverter passengerConverter;

    public PassengerController(PassengerService passengerService, PassengerConverter passengerConverter) {
        this.passengerService = passengerService;
        this.passengerConverter = passengerConverter;
    }

    /**
     * Creates a new passenger in the system.
     *
     * This endpoint allows clients to create a new passenger by providing the passenger's details.
     * The details are encapsulated in a PassengerDTO object.
     *
     * @param passengerDTO the details of the passenger to be created
     * @return ResponseEntity containing the created PassengerDTO and an HTTP status of 201 (Created)
     */
    @Operation(summary = "Create a new passenger")
    @PostMapping(value="/create", consumes = "application/json")
    public ResponseEntity<PassengerDTO> createPassenger(@RequestBody PassengerDTO passengerDTO) {
        Passenger passenger = passengerConverter.dtoToPassenger(passengerDTO);
        Passenger newPassenger = passengerService.createPassenger(passenger);
        PassengerDTO newPassengerDTO = passengerConverter.passengerToDto(newPassenger);
        return new ResponseEntity<>(newPassengerDTO, HttpStatus.CREATED);
    }

    /**
     * Retrieves a passenger by their ID.
     *
     * This endpoint allows clients to fetch a specific passenger's details using their unique ID.
     *
     * @param id the ID of the passenger to be retrieved
     * @return ResponseEntity containing the PassengerDTO if found, otherwise an HTTP status of 404 (Not Found)
     */
    @Operation(summary = "Get a passenger by ID")
    @GetMapping("/{id}")
    public ResponseEntity<PassengerDTO> getPassengerById(@Parameter(description = "ID of the passenger to be retrieved") @PathVariable Long id) {
        Passenger passenger = passengerService.getPassengerById(id);
        if (passenger != null) {
            PassengerDTO passengerDTO = passengerConverter.passengerToDto(passenger);
            return new ResponseEntity<>(passengerDTO, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    /**
     * Retrieves all passengers from the system.
     *
     * This endpoint fetches a list of all passengers currently registered in the system.
     *
     * @return ResponseEntity containing a list of PassengerDTOs and an HTTP status of 200 (OK)
     */
    @Operation(summary = "Get all passengers")
    @GetMapping
    public ResponseEntity<List<PassengerDTO>> getAllPassengers() {
        List<Passenger> passengers = passengerService.getAllPassengers();
        List<PassengerDTO> passengerDTOs = passengerConverter.passengersToDtoList(passengers);
        return new ResponseEntity<>(passengerDTOs, HttpStatus.OK);
    }

    /**
     * Updates the details of an existing passenger.
     *
     * This endpoint allows clients to update the information of a specific passenger
     * using their ID and the new details provided in a PassengerDTO.
     *
     * @param id the ID of the passenger to be updated
     * @param passengerDTO the new details for the passenger
     * @return ResponseEntity containing the updated PassengerDTO if successful,
     *         otherwise an HTTP status of 404 (Not Found)
     */
    @Operation(summary = "Update a passenger")
    @PutMapping("/update/{id}")
    public ResponseEntity<PassengerDTO> updatePassenger(@Parameter(description = "ID of the passenger to be updated") @PathVariable Long id, @RequestBody PassengerDTO passengerDTO) {
        Passenger passenger = passengerConverter.dtoToPassenger(passengerDTO);
        Passenger updatedPassenger = passengerService.updatePassenger(id, passenger);
        if (updatedPassenger != null) {
            PassengerDTO updatedPassengerDTO = passengerConverter.passengerToDto(updatedPassenger);
            return new ResponseEntity<>(updatedPassengerDTO, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    /**
     * Deletes a passenger by their ID.
     *
     * This endpoint allows clients to remove a specific passenger from the system
     * using their unique ID.
     *
     * @param id the ID of the passenger to be deleted
     * @return ResponseEntity with HTTP status of 204 (No Content) if deletion is successful,
     *         otherwise an HTTP status of 404 (Not Found)
     */
    @Operation(summary = "Delete a passenger by ID")
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deletePassenger(@Parameter(description = "ID of the passenger to be deleted") @PathVariable Long id) {
        boolean isDeleted = passengerService.deletePassenger(id);
        if (isDeleted) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
