package com.flightbookings.flight_bookings.controllers;

import com.flightbookings.flight_bookings.dtos.DTOFlight.FlightConverter;
import com.flightbookings.flight_bookings.dtos.DTOFlight.FlightDTO;
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

@CrossOrigin("*")
@RestController
@RequestMapping("api/v1/flight")
@Tag(name = "Flight", description = "Operations pertaining to flight management")
public class FlightController {

    private final FlightService flightService;
    private final FlightConverter flightConverter;

    public FlightController(FlightService flightService, FlightConverter flightConverter) {
        this.flightService = flightService;
        this.flightConverter = flightConverter;
    }

    @Operation(summary = "Create a new flight")
    @PostMapping(value = "/create", consumes = "application/json")
    public ResponseEntity<FlightDTO> createFlight(@RequestBody FlightDTO flightDTO) {
        Flight flight = flightConverter.dtoToFlight(flightDTO);
        Flight newFlight = flightService.createFlight(flight);
        FlightDTO newFlightDTO = flightConverter.flightToDto(newFlight);
        return new ResponseEntity<>(newFlightDTO, HttpStatus.CREATED);
    }

    @Operation(summary = "Get flight by ID")
    @GetMapping("/{id}")
    public ResponseEntity<FlightDTO> getFlightById(@Parameter(description = "ID of the flight to be retrieved") @PathVariable Long id) {
        Flight flight = flightService.getFlightById(id);
        if (flight != null) {
            FlightDTO flightDTO = flightConverter.flightToDto(flight);
            return new ResponseEntity<>(flightDTO, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @Operation(summary = "Get all flights")
    @GetMapping("/")
    public ResponseEntity<List<FlightDTO>> getAllFlights() {
        List<Flight> flights = flightService.getAllFlights();
        List<FlightDTO> flightDTOs = flightConverter.flightsToDtoList(flights);
        return new ResponseEntity<>(flightDTOs, HttpStatus.OK);
    }

    @Operation(summary = "Update an existing flight")
    @PutMapping("/update/{id}")
    public ResponseEntity<FlightDTO> updateFlight(@Parameter(description = "ID of the flight to be retrieved") @PathVariable Long id, @RequestBody FlightDTO flightDTO) {
        Flight flight = flightConverter.dtoToFlight(flightDTO);
        Flight updatedFlight = flightService.updateFlight(id, flight);
        if (updatedFlight != null) {
            FlightDTO updatedFlightDTO = flightConverter.flightToDto(updatedFlight);
            return new ResponseEntity<>(updatedFlightDTO, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @Operation(summary = "Delete a flight by ID")
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteFlight(@Parameter(description = "ID of the flight to be retrieved") @PathVariable Long id) {
        boolean isDeleted = flightService.deleteFlight(id);
        if (isDeleted) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

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
    public ResponseEntity<List<FlightDTO>> getFlightsByAirplaneType(@RequestParam EFlightAirplane airplaneType) {
        List<Flight> flights = flightService.getFlightsByAirplaneType(airplaneType);
        List<FlightDTO> flightDTOs = flightConverter.flightsToDtoList(flights);
        return new ResponseEntity<>(flightDTOs, HttpStatus.OK);
    }
}
