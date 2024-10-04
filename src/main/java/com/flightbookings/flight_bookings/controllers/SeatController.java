package com.flightbookings.flight_bookings.controllers;

import com.flightbookings.flight_bookings.services.interfaces.FlightService;
import com.flightbookings.flight_bookings.services.interfaces.SeatService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Controller for managing seat-related operations such as seat initialization.
 */
@CrossOrigin("*")
@RestController
@RequestMapping("api/v1/seat")
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
    @PostMapping("/initialize")
    public ResponseEntity<String> initializeSeats() {
        seatService.initializeSeatsForAllFlights();
        return ResponseEntity.ok("Seats initialized for all flights.");
    }
}
