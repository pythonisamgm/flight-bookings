package com.flightbookings.flight_bookings.controllers;

import com.flightbookings.flight_bookings.dtos.DTOSeat.SeatDTO;
import com.flightbookings.flight_bookings.models.Flight;
import com.flightbookings.flight_bookings.services.interfaces.FlightService;
import com.flightbookings.flight_bookings.services.interfaces.SeatService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Controller for managing seat-related operations such as seat initialization.
 */
@CrossOrigin("*")
@RestController
@RequestMapping("api/v1/seat")
@Tag(name = "Seat Management", description = "Operations pertaining to seat management")
public class SeatController {

    private final SeatService seatService;
    private final FlightService flightService;

    /**
     * Constructor to initialize the SeatController with SeatService and FlightService.
     *
     * @param seatService   the seat service for managing seat operations.
     * @param flightService the flight service for managing flight operations.
     */
    public SeatController(SeatService seatService, FlightService flightService) {
        this.seatService = seatService;
        this.flightService = flightService;
    }

    /**
     * Initializes seats for all flights.
     *
     * @return a response indicating successful initialization.
     */
    @Operation(summary = "Initialize seats for all flights")
    @PostMapping("/initialize")
    public ResponseEntity<String> initializeSeats() {
        seatService.initializeSeatsForAllFlights();
        return ResponseEntity.ok("Seats initialized for all flights.");
    }

    /**
     * Retrieves seats for a specific flight.
     *
     * @param flightId the ID of the flight to retrieve seats for.
     * @return a list of seats for the specified flight.
     */
    @Operation(summary = "Get seats for a specific flight")
    @GetMapping("/flight/{flightId}/seats")
    public ResponseEntity<List<SeatDTO>> getSeatsByFlightId(
            @Parameter(description = "ID of the flight to retrieve seats for") @PathVariable Long flightId) {
        Flight flight = flightService.getFlightById(flightId);
        if (flight == null) {
            return ResponseEntity.notFound().build();
        }

        List<SeatDTO> seats = seatService.getSeatsByFlight(flight);
        return ResponseEntity.ok(seats);
    }
}
