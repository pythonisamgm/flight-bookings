package com.flightbookings.flight_bookings.controllers;

import com.flightbookings.flight_bookings.models.Passenger;
import com.flightbookings.flight_bookings.services.interfaces.PassengerService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin("*")
@RestController
@RequestMapping("/api/v1/passengers")
@Api(tags = "Passenger Management", description = "Operations pertaining to passenger management")
public class PassengerController {

    private final PassengerService passengerService;

    public PassengerController(PassengerService passengerService) {
        this.passengerService = passengerService;
    }

    @ApiOperation(value = "Create a new passenger", response = Passenger.class)
    @PostMapping(value="/create",consumes = "application/json")
    public ResponseEntity<Passenger> createPassenger(@RequestBody Passenger passenger) {
        Passenger newPassenger = passengerService.createPassenger(passenger);
        return new ResponseEntity<>(newPassenger, HttpStatus.CREATED);
    }

    @ApiOperation(value = "Get a passenger by ID", response = Passenger.class)
    @GetMapping("/{id}")
    public ResponseEntity<Passenger> getPassengerById(@PathVariable Long id) {
        Passenger passenger = passengerService.getPassengerById(id);
        if (passenger != null) {
            return new ResponseEntity<>(passenger, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
    @ApiOperation(value = "Get all passengers", response = Passenger.class)
    @GetMapping
    public ResponseEntity<List<Passenger>> getAllPassengers() {
        List<Passenger> passengers = passengerService.getAllPassengers();
        return new ResponseEntity<>(passengers, HttpStatus.OK);
    }
    @ApiOperation(value = "Update a passenger", response = Passenger.class)
    @PutMapping("/update/{id}")
    public ResponseEntity<Passenger> updatePassenger(@PathVariable Long id, @RequestBody Passenger passengerDetails) {
        Passenger updatedPassenger = passengerService.updatePassenger(id, passengerDetails);
        if (updatedPassenger != null) {
            return new ResponseEntity<>(updatedPassenger, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
    @ApiOperation(value = "Delete a passenger by ID", response = Passenger.class)
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deletePassenger(@PathVariable Long id) {
        boolean isDeleted = passengerService.deletePassenger(id);
        if (isDeleted) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
