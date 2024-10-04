package com.flightbookings.flight_bookings.controllers;

import com.flightbookings.flight_bookings.dtos.DTOPassenger.PassengerDTO;
import com.flightbookings.flight_bookings.dtos.DTOPassenger.PassengerConverter;
import com.flightbookings.flight_bookings.models.Passenger;
import com.flightbookings.flight_bookings.services.interfaces.PassengerService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin("*")
@RestController
@RequestMapping("/api/v1/passengers")
@Tag(name = "Passenger Management", description = "Operations pertaining to passenger management")
public class PassengerController {

    private final PassengerService passengerService;
    private final PassengerConverter passengerConverter;

    public PassengerController(PassengerService passengerService, PassengerConverter passengerConverter) {
        this.passengerService = passengerService;
        this.passengerConverter = passengerConverter;
    }

    @Operation(summary = "Create a new passenger")
    @PostMapping(value = "/create", consumes = "application/json")
    public ResponseEntity<PassengerDTO> createPassenger(@Valid @RequestBody PassengerDTO passengerDTO) {
        Passenger passenger = passengerConverter.dtoToPassenger(passengerDTO);
        Passenger newPassenger = passengerService.createPassenger(passenger);
        return ResponseEntity.status(HttpStatus.CREATED).body(passengerConverter.passengerToDto(newPassenger));
    }

    @Operation(summary = "Get a passenger by ID")
    @GetMapping("/{id}")
    public ResponseEntity<PassengerDTO> getPassengerById(@Parameter(description = "ID of the passenger to be retrieved") @PathVariable Long id) {
        Passenger passenger = passengerService.getPassengerById(id);
        return passenger != null
                ? ResponseEntity.ok(passengerConverter.passengerToDto(passenger))
                : ResponseEntity.notFound().build();
    }

    @Operation(summary = "Get all passengers")
    @GetMapping
    public ResponseEntity<List<PassengerDTO>> getAllPassengers() {
        List<Passenger> passengers = passengerService.getAllPassengers();
        List<PassengerDTO> passengerDTOs = passengerConverter.passengersToDtoList(passengers);
        return ResponseEntity.ok(passengerDTOs);
    }

    @Operation(summary = "Update a passenger")
    @PutMapping("/{id}")
    public ResponseEntity<PassengerDTO> updatePassenger(
            @Parameter(description = "ID of the passenger to be updated") @PathVariable Long id,
            @Valid @RequestBody PassengerDTO passengerDTO) {

        Passenger updatedPassenger = passengerService.updatePassenger(id, passengerConverter.dtoToPassenger(passengerDTO));
        return updatedPassenger != null
                ? ResponseEntity.ok(passengerConverter.passengerToDto(updatedPassenger))
                : ResponseEntity.notFound().build();
    }

    @Operation(summary = "Delete a passenger by ID")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePassenger(@Parameter(description = "ID of the passenger to be deleted") @PathVariable Long id) {
        boolean isDeleted = passengerService.deletePassenger(id);
        return isDeleted ? ResponseEntity.noContent().build() : ResponseEntity.notFound().build();
    }
}
