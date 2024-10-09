package com.flightbookings.flight_bookings.controllers;

import com.flightbookings.flight_bookings.dtos.DTOSeat.SeatConverter;
import com.flightbookings.flight_bookings.services.interfaces.FlightService;
import com.flightbookings.flight_bookings.services.interfaces.SeatService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin("*")
@RestController
@RequestMapping("api/v1/seat")
@Tag(name = "Seat Management", description = "Operations pertaining to seat management")
public class SeatController {

    private final SeatService seatService;
    private final FlightService flightService;
    private final SeatConverter seatConverter;

    public SeatController(SeatService seatService, FlightService flightService, SeatConverter seatConverter) {
        this.seatService = seatService;
        this.flightService = flightService;
        this.seatConverter = seatConverter;
    }

    /**
     * Initializes seats for all flights in the system.
     * This method will allocate and set up the seats for every flight currently available
     * in the system. This is useful for setting up the initial seat configuration for
     * new flights.
     *
     * @return ResponseEntity containing a message confirming seat initialization and HTTP status code
     */
    @Operation(summary = "Initialize seats for all flights")
    @PostMapping("/initialize")
    public ResponseEntity<String> initializeSeats() {
        try {
            seatService.initializeSeatsForAllFlights();
            return ResponseEntity.ok("Seats initialized for all flights.");
        } catch (RuntimeException e) {
            return ResponseEntity.status(500).body("Error initializing seats: " + e.getMessage());
        }
    }
}
