package com.flightbookings.flight_bookings.controllers;

import com.flightbookings.flight_bookings.dtos.DTOSeat.SeatDTO;
import com.flightbookings.flight_bookings.models.Flight;
import com.flightbookings.flight_bookings.services.interfaces.SeatService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin("*")
@RestController
@RequestMapping("api/v1/seat")
@Tag(name = "Seat Management", description = "Operations pertaining to seat management")
public class SeatController {

    private final SeatService seatService;

    public SeatController(SeatService seatService) {
        this.seatService = seatService;
    }

    @Operation(summary = "Initialize seats for a flight")
    @PostMapping("/initialize")
    public List<String> initializeSeats(@Parameter(description = "Flight object") @RequestBody Flight flight,
                                        @Parameter(description = "Number of rows") @RequestParam int numRows) {
        return seatService.initializeSeats(flight, numRows);
    }

    @Operation(summary = "Reserve a seat")
    @PutMapping("/reserve/{seatName}")
    public SeatDTO reserveSeat(@Parameter(description = "Name of the seat to be reserved") @PathVariable String seatName,
                               @Parameter(description = "Flight object") @RequestBody Flight flight) {
        return seatService.reserveSeat(flight, seatName);
    }
}
