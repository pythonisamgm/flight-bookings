package com.flightbookings.flight_bookings.controllers;

import com.flightbookings.flight_bookings.services.interfaces.FlightService;
import com.flightbookings.flight_bookings.services.interfaces.SeatService;
import lombok.RequiredArgsConstructor;
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
@RequiredArgsConstructor
public class SeatController {

    private final SeatService seatService;
    private final FlightService flightService;

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

