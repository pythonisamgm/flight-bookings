package com.flightbookings.flight_bookings.controllers;


import com.flightbookings.flight_bookings.models.Flight;
import com.flightbookings.flight_bookings.services.interfaces.FlightService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin("*")
@RestController
@RequestMapping("api/v1/flight")
@Api(tags = "Flight Management", description = "Operations pertaining to flight management")
public class FlightController {

    private final FlightService flightService;

    public FlightController(FlightService flightService) {
        this.flightService = flightService;
    }

    @ApiOperation(value = "Create a new flight", response = Flight.class)
    @PostMapping(value="/create",consumes = "application/json")
    public ResponseEntity<Flight> createFlight(@RequestBody Flight flight) {
        Flight newFlight = flightService.createFlight(flight);
        return new ResponseEntity<>(newFlight, HttpStatus.CREATED);
    }

    @ApiOperation(value = "Get flight by ID", response = Flight.class)
    @GetMapping("/{id}")
    public ResponseEntity<Flight> getFlightById(@PathVariable Long id) {
        Flight flight = flightService.getFlightById(id);
        return flight != null ? new ResponseEntity<>(flight, HttpStatus.OK) : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @ApiOperation(value = "Get all flights", response = List.class)
    @GetMapping("/")
    public ResponseEntity<List<Flight>> getAllFlights() {
        List<Flight> flights = flightService.getAllFlights();
        return new ResponseEntity<>(flights, HttpStatus.OK);
    }

    @ApiOperation(value = "Update an existing flight", response = Flight.class)
    @PutMapping("/update/{id}")
    public ResponseEntity<Flight> updateFlight(@PathVariable Long id, @RequestBody Flight flightDetails) {
        Flight updatedFlight = flightService.updateFlight(id, flightDetails);
        return updatedFlight != null ? new ResponseEntity<>(updatedFlight, HttpStatus.OK) : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @ApiOperation(value = "Delete a flight by ID")
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteFlight(@PathVariable Long id) {
        boolean isDeleted = flightService.deleteFlight(id);
        return isDeleted ? new ResponseEntity<>(HttpStatus.NO_CONTENT) : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

}
