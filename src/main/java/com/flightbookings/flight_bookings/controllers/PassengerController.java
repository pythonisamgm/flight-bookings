package com.flightbookings.flight_bookings.controllers;

import com.flightbookings.flight_bookings.dtos.DTOPassenger.PassengerDTO;
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

    public PassengerController(PassengerService passengerService) {
        this.passengerService = passengerService;
    }

    @Operation(summary = "Create a new passenger")
    @PostMapping(consumes = "application/json")
    public ResponseEntity<PassengerDTO> createPassenger(@RequestBody PassengerDTO passengerDTO) {
        PassengerDTO newPassenger = passengerService.createPassenger(passengerDTO);
        return new ResponseEntity<>(newPassenger, HttpStatus.CREATED);
    }

    @Operation(summary = "Get a passenger by ID")
    @GetMapping("/{id}")
    public ResponseEntity<PassengerDTO> getPassengerById(
            @Parameter(description = "ID of the passenger to be retrieved") @PathVariable Long id) {
        PassengerDTO passengerDTO = passengerService.getPassengerById(id);
        return passengerDTO != null ?
                new ResponseEntity<>(passengerDTO, HttpStatus.OK) :
                ResponseEntity.notFound().build();
    }

    @Operation(summary = "Get all passengers")
    @GetMapping
    public ResponseEntity<List<PassengerDTO>> getAllPassengers() {
        List<PassengerDTO> passengers = passengerService.getAllPassengers();
        return new ResponseEntity<>(passengers, HttpStatus.OK);
    }

    @Operation(summary = "Update a passenger")
    @PutMapping("/{id}")
    public ResponseEntity<PassengerDTO> updatePassenger(
            @Parameter(description = "ID of the passenger to be updated") @PathVariable Long id,
            @RequestBody PassengerDTO passengerDetails) {
        PassengerDTO updatedPassenger = passengerService.updatePassenger(id, passengerDetails);
        return updatedPassenger != null ?
                new ResponseEntity<>(updatedPassenger, HttpStatus.OK) :
                ResponseEntity.notFound().build();
    }

    @Operation(summary = "Delete a passenger by ID")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePassenger(
            @Parameter(description = "ID of the passenger to be deleted") @PathVariable Long id) {
        boolean isDeleted = passengerService.deletePassenger(id);
        return isDeleted ?
                ResponseEntity.noContent().build() :
                ResponseEntity.notFound().build();
    }
}
