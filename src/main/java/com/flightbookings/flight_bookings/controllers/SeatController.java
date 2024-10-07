package com.flightbookings.flight_bookings.controllers;

import com.flightbookings.flight_bookings.dtos.DTOSeat.SeatDTO;
import com.flightbookings.flight_bookings.dtos.DTOSeat.SeatConverter;
import com.flightbookings.flight_bookings.models.Seat;
import com.flightbookings.flight_bookings.services.interfaces.FlightService;
import com.flightbookings.flight_bookings.services.interfaces.SeatService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@CrossOrigin("*")
@RestController
@RequestMapping("api/v1/seat")
public class SeatController {

    private final SeatService seatService;
    private final FlightService flightService;
    private final SeatConverter seatConverter;

    public SeatController(SeatService seatService, FlightService flightService, SeatConverter seatConverter) {
        this.seatService = seatService;
        this.flightService = flightService;
        this.seatConverter = seatConverter;
    }

    @PostMapping("/initialize")
    public ResponseEntity<String> initializeSeats() {
        seatService.initializeSeatsForAllFlights();
        return ResponseEntity.ok("Seats initialized for all flights.");
    }

}
