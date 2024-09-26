package com.flightbookings.flight_bookings.controllers;

import com.flightbookings.flight_bookings.services.FlightApiService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class FlightApiController {

    @Autowired
    private FlightApiService flightApiService;

    @GetMapping("/getflight")
    public ResponseEntity<String> getFlights(@RequestParam String from,
                                             @RequestParam String to,
                                             @RequestParam String dateFrom,
                                             @RequestParam String dateTo) {
        try {
            String response = flightApiService.searchFlights(from, to, dateFrom, dateTo);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Error: " + e.getMessage());
        }
    }
}
