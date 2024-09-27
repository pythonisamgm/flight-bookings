package com.flightbookings.flight_bookings.controllers;

import com.flightbookings.flight_bookings.models.Flight;
import com.flightbookings.flight_bookings.models.EFlightAirplane;
import com.flightbookings.flight_bookings.services.interfaces.FlightService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@CrossOrigin("*")
@RestController
@RequestMapping("api/v1/flight")
public class FlightController {

    private final FlightService flightService;

    public FlightController(FlightService flightService) {
        this.flightService = flightService;
    }

    @PostMapping("/create")
    public ResponseEntity<Flight> createFlight(@RequestBody Flight flight) {
        Flight newFlight = flightService.createFlight(flight);
        return new ResponseEntity<>(newFlight, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Flight> getFlightById(@PathVariable Long id) {
        Flight flight = flightService.getFlightById(id);
        return flight != null ? new ResponseEntity<>(flight, HttpStatus.OK) : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @GetMapping("/")
    public ResponseEntity<List<Flight>> getAllFlights() {
        List<Flight> flights = flightService.getAllFlights();
        return new ResponseEntity<>(flights, HttpStatus.OK);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<Flight> updateFlight(@PathVariable Long id, @RequestBody Flight flightDetails) {
        Flight updatedFlight = flightService.updateFlight(id, flightDetails);
        return updatedFlight != null ? new ResponseEntity<>(updatedFlight, HttpStatus.OK) : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteFlight(@PathVariable Long id) {
        boolean isDeleted = flightService.deleteFlight(id);
        return isDeleted ? new ResponseEntity<>(HttpStatus.NO_CONTENT) : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

//    @GetMapping("/search")
//    public ResponseEntity<List<Flight>> searchFlightsByCity(@RequestParam String city) {
//        List<Flight> flights = flightService.searchFlightsByCity(city);
//        return new ResponseEntity<>(flights, HttpStatus.OK);
//    }

    @DeleteMapping("/{id}/cancel")
    public ResponseEntity<String> cancelFlight(@PathVariable Long id) {
        flightService.cancelFlight(id);
        return ResponseEntity.ok("Flight canceled successfully.");
    }

    @PostMapping("/{id}/delay")
    public ResponseEntity<String> delayFlight(@PathVariable Long id, @RequestParam String newDepartureTime) {
        LocalDateTime departureTime = LocalDateTime.parse(newDepartureTime);
        flightService.delayFlight(id, departureTime);
        return ResponseEntity.ok("Flight delayed successfully.");
    }

    @PostMapping("/updateAvailability")
    public ResponseEntity<String> updateAvailability() {
        flightService.updateFlightAvailability();
        return ResponseEntity.ok("Flight availability updated successfully.");
    }

    @GetMapping("/byAirplaneType")
    public ResponseEntity<List<Flight>> getFlightsByAirplaneType(@RequestParam EFlightAirplane airplaneType) {
        List<Flight> flights = flightService.getFlightsByAirplaneType(airplaneType);
        return new ResponseEntity<>(flights, HttpStatus.OK);
    }
}
