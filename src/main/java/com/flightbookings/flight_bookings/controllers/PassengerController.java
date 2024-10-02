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
    @PostMapping(value="/create", consumes = "application/json")
    public ResponseEntity<PassengerDTO> createPassenger(@RequestBody PassengerDTO passengerDTO) {
        PassengerDTO newPassenger = passengerService.createPassenger(passengerDTO);
        return new ResponseEntity<>(newPassenger, HttpStatus.CREATED);
    }

    @Operation(summary = "Get a passenger by ID")
    @GetMapping("/{id}")
    public ResponseEntity<PassengerDTO> getPassengerById(@Parameter(description = "ID of the passenger to be retrieved") @PathVariable Long id) {
        PassengerDTO passengerDTO = passengerService.getPassengerById(id);
        if (passengerDTO != null) {
            return new ResponseEntity<>(passengerDTO, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @Operation(summary = "Get all passengers")
    @GetMapping
    public ResponseEntity<List<PassengerDTO>> getAllPassengers() {
        List<PassengerDTO> passengers = passengerService.getAllPassengers();
        return new ResponseEntity<>(passengers, HttpStatus.OK);
    }

    @Operation(summary = "Update a passenger")
    @PutMapping("/update/{id}")
    public ResponseEntity<PassengerDTO> updatePassenger(@Parameter(description = "ID of the passenger to be updated") @PathVariable Long id, @RequestBody PassengerDTO passengerDetails) {
        PassengerDTO updatedPassenger = passengerService.updatePassenger(id, passengerDetails);
        if (updatedPassenger != null) {
            return new ResponseEntity<>(updatedPassenger, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

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
