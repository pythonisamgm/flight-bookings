package com.flightbookings.flight_bookings.controllers;

import com.flightbookings.flight_bookings.dtos.DTOFlight.FlightDTO;
import com.flightbookings.flight_bookings.dtos.DTOFlight.FlightConverter;
import com.flightbookings.flight_bookings.models.Flight;
import com.flightbookings.flight_bookings.models.EFlightAirplane;
import com.flightbookings.flight_bookings.services.interfaces.FlightService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
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
    public ResponseEntity<FlightDTO> createFlight(@Valid @RequestBody FlightDTO flightDTO) {
        Flight flight = flightConverter.dtoToFlight(flightDTO);
        Flight newFlight = flightService.createFlight(flight);
        return ResponseEntity.status(HttpStatus.CREATED).body(flightConverter.flightToDto(newFlight));
    }

    @Operation(summary = "Get flight by ID")
    @GetMapping("/{id}")
    public ResponseEntity<FlightDTO> getFlightById(@Parameter(description = "ID of the flight to be retrieved") @PathVariable Long id) {
        Flight flight = flightService.getFlightById(id);
        return flight != null ? ResponseEntity.ok(flightConverter.flightToDto(flight)) : ResponseEntity.notFound().build();
    }

    @Operation(summary = "Get all flights")
    @GetMapping("/")
    public ResponseEntity<List<FlightDTO>> getAllFlights() {
        List<Flight> flights = flightService.getAllFlights();
        List<FlightDTO> flightDTOs = flightConverter.flightsToDtoList(flights);
        return ResponseEntity.ok(flightDTOs);
    }

    @Operation(summary = "Update an existing flight")
    @PutMapping("/update/{id}")
    public ResponseEntity<FlightDTO> updateFlight(
            @Parameter(description = "ID of the flight to be updated") @PathVariable Long id,
            @Valid @RequestBody FlightDTO flightDTO) {

        Flight flight = flightConverter.dtoToFlight(flightDTO);
        Flight updatedFlight = flightService.updateFlight(id, flight);
        return updatedFlight != null ? ResponseEntity.ok(flightConverter.flightToDto(updatedFlight)) : ResponseEntity.notFound().build();
    }

    @Operation(summary = "Delete a flight by ID")
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteFlight(@Parameter(description = "ID of the flight to be deleted") @PathVariable Long id) {
        boolean isDeleted = flightService.deleteFlight(id);
        return isDeleted ? ResponseEntity.noContent().build() : ResponseEntity.notFound().build();
    }

    @Operation(summary = "Cancel a flight by ID")
    @DeleteMapping("/{id}/cancel")
    public ResponseEntity<String> cancelFlight(@Parameter(description = "ID of the flight to be canceled") @PathVariable Long id) {
        boolean isCancelled = flightService.cancelFlight(id);
        return isCancelled ? ResponseEntity.ok("Flight canceled successfully.") : ResponseEntity.notFound().build();
    }

    @Operation(summary = "Delay a flight")
    @PostMapping("/{id}/delay")
    public ResponseEntity<String> delayFlight(@Parameter(description = "ID of the flight to be delayed") @PathVariable Long id, @RequestParam String newDepartureTime) {
        LocalDateTime departureTime = LocalDateTime.parse(newDepartureTime);
        boolean isDelayed = flightService.delayFlight(id, departureTime);
        return isDelayed ? ResponseEntity.ok("Flight delayed successfully.") : ResponseEntity.notFound().build();
    }

    @Operation(summary = "Update availability of all flights")
    @PostMapping("/updateAvailability")
    public ResponseEntity<String> updateAvailability() {
        flightService.updateFlightAvailability();
        return ResponseEntity.ok("Flight availability updated successfully.");
    }

    @Operation(summary = "Get flights by airplane type")
    @GetMapping("/byAirplaneType")
    public ResponseEntity<List<FlightDTO>> getFlightsByAirplaneType(@Parameter(description = "Type of airplane to filter flights") @RequestParam EFlightAirplane airplaneType) {
        List<Flight> flights = flightService.getFlightsByAirplaneType(airplaneType);
        List<FlightDTO> flightDTOs = flightConverter.flightsToDtoList(flights);
        return ResponseEntity.ok(flightDTOs);
    }
}
