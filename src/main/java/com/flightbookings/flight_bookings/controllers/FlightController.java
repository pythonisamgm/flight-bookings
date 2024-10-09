package com.flightbookings.flight_bookings.controllers;

import com.flightbookings.flight_bookings.dtos.DTOFlight.FlightConverter;
import com.flightbookings.flight_bookings.dtos.DTOFlight.FlightDTO;
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

    /**
     * Creates a new flight in the system.
     *
     * This endpoint allows clients to create a new flight by providing its details
     * encapsulated in a FlightDTO object.
     *
     * @param flightDTO the details of the flight to be created
     * @return ResponseEntity containing the created FlightDTO and an HTTP status of 201 (Created)
     */
    @Operation(summary = "Create a new flight")
    @PostMapping(value = "/create", consumes = "application/json")
    public ResponseEntity<FlightDTO> createFlight(@Valid @RequestBody FlightDTO flightDTO) {
        Flight flight = flightConverter.dtoToFlight(flightDTO);
        Flight newFlight = flightService.createFlight(flight);
        FlightDTO newFlightDTO = flightConverter.flightToDto(newFlight);
        return new ResponseEntity<>(newFlightDTO, HttpStatus.CREATED);
    }

    /**
     * Retrieves a flight by its ID.
     *
     * This endpoint allows clients to fetch the details of a specific flight using its unique ID.
     *
     * @param id the ID of the flight to be retrieved
     * @return ResponseEntity containing the FlightDTO if found, otherwise an HTTP status of 404 (Not Found)
     */
    @Operation(summary = "Get flight by ID")
    @GetMapping("/{id}")
    public ResponseEntity<FlightDTO> getFlightById(
            @Parameter(description = "ID of the flight to be retrieved") @PathVariable Long id) {
        Flight flight = flightService.getFlightById(id);
        if (flight != null) {
            FlightDTO flightDTO = flightConverter.flightToDto(flight);
            return new ResponseEntity<>(flightDTO, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    /**
     * Retrieves all flights from the system.
     *
     * This endpoint fetches a list of all flights currently available in the system.
     *
     * @return ResponseEntity containing a list of FlightDTOs and an HTTP status of 200 (OK)
     */
    @Operation(summary = "Get all flights")
    @GetMapping("/")
    public ResponseEntity<List<FlightDTO>> getAllFlights() {
        List<Flight> flights = flightService.getAllFlights();
        List<FlightDTO> flightDTOs = flightConverter.flightsToDtoList(flights);
        return new ResponseEntity<>(flightDTOs, HttpStatus.OK);
    }

    /**
     * Updates the details of an existing flight.
     *
     * This endpoint allows clients to update the information of a specific flight
     * using its ID and the new details provided in a FlightDTO.
     *
     * @param id the ID of the flight to be updated
     * @param flightDTO the new details for the flight
     * @return ResponseEntity containing the updated FlightDTO if successful,
     *         otherwise an HTTP status of 404 (Not Found)
     */
    @Operation(summary = "Update an existing flight")
    @PutMapping("/update/{id}")
    public ResponseEntity<FlightDTO> updateFlight(
            @Parameter(description = "ID of the flight to be updated") @PathVariable Long id,
            @Valid @RequestBody FlightDTO flightDTO) {
        Flight flight = flightConverter.dtoToFlight(flightDTO);
        Flight updatedFlight = flightService.updateFlight(id, flight);
        if (updatedFlight != null) {
            FlightDTO updatedFlightDTO = flightConverter.flightToDto(updatedFlight);
            return new ResponseEntity<>(updatedFlightDTO, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    /**
     * Deletes a flight by its ID.
     *
     * This endpoint allows clients to remove a specific flight from the system
     * using its unique ID.
     *
     * @param id the ID of the flight to be deleted
     * @return ResponseEntity with HTTP status of 204 (No Content) if deletion is successful,
     *         otherwise an HTTP status of 404 (Not Found)
     */
    @Operation(summary = "Delete a flight by ID")
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteFlight(
            @Parameter(description = "ID of the flight to be deleted") @PathVariable Long id) {
        boolean isDeleted = flightService.deleteFlight(id);
        if (isDeleted) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    /**
     * Cancels a flight by its ID.
     *
     * This endpoint allows clients to cancel a specific flight using its unique ID.
     *
     * @param id the ID of the flight to be canceled
     * @return ResponseEntity with a message indicating the flight has been canceled successfully
     */
    @Operation(summary = "Cancel a flight by ID")
    @DeleteMapping("/{id}/cancel")
    public ResponseEntity<String> cancelFlight(@Parameter(description = "ID of the flight to be canceled") @PathVariable Long id) {
        flightService.cancelFlight(id);
        return ResponseEntity.ok("Flight canceled successfully.");
    }

    /**
     * Delays a flight by its ID.
     *
     * This endpoint allows clients to delay a specific flight by providing a new departure time.
     *
     * @param id the ID of the flight to be delayed
     * @param newDepartureTime the new departure time in ISO-8601 format
     * @return ResponseEntity with a message indicating the flight has been delayed successfully
     */
    @Operation(summary = "Delay a flight by ID")
    @PostMapping("/{id}/delay")
    public ResponseEntity<String> delayFlight(
            @Parameter(description = "ID of the flight to be delayed") @PathVariable Long id,
            @Parameter(description = "New departure time in ISO-8601 format") @RequestParam String newDepartureTime) {
        LocalDateTime departureTime = LocalDateTime.parse(newDepartureTime);
        flightService.delayFlight(id, departureTime);
        return ResponseEntity.status(HttpStatus.ACCEPTED).body("Flight delayed successfully."); // Cambiado a 202 Accepted
    }

    /**
     * Updates the availability of all flights.
     *
     * This endpoint allows clients to update the availability status of all flights.
     *
     * @return ResponseEntity with a message indicating the flight availability has been updated successfully
     */
    @Operation(summary = "Update availability of all flights")
    @PostMapping("/updateAvailability")
    public ResponseEntity<String> updateAvailability() {
        flightService.updateFlightAvailability();
        return ResponseEntity.ok("Flight availability updated successfully.");
    }

    /**
     * Retrieves flights filtered by airplane type.
     *
     * This endpoint allows clients to fetch a list of flights filtered by the specified airplane type.
     *
     * @param airplaneType the type of airplane to filter flights
     * @return ResponseEntity containing a list of FlightDTOs filtered by airplane type and an HTTP status of 200 (OK)
     */
    @Operation(summary = "Get flights by airplane type")
    @GetMapping("/byAirplaneType")
    public ResponseEntity<List<FlightDTO>> getFlightsByAirplaneType(
            @Parameter(description = "Type of airplane to filter flights") @RequestParam EFlightAirplane airplaneType) {
        List<Flight> flights = flightService.getFlightsByAirplaneType(airplaneType);
        List<FlightDTO> flightDTOs = flightConverter.flightsToDtoList(flights);
        return new ResponseEntity<>(flightDTOs, HttpStatus.OK);
    }
}
