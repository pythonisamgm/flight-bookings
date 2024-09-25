package com.flightbookings.flight_bookings.controllers;

import com.fasterxml.jackson.databind.JsonNode;
import com.flightbookings.flight_bookings.services.interfaces.AirLabsService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class AirLabsController {

    private final AirLabsService airLabsService;

    public AirLabsController(AirLabsService airLabsService) {
        this.airLabsService = airLabsService;
    }

    @GetMapping("/getflights")
    public ResponseEntity<JsonNode> getFlights(@RequestParam String originCity, @RequestParam String destinationCity) {
        String originIata = airLabsService.getIataCodeForCity(originCity);
        String destinationIata = airLabsService.getIataCodeForCity(destinationCity);

        if (!originIata.isEmpty() && !destinationIata.isEmpty()) {
            JsonNode flights = airLabsService.searchFlights(originIata, destinationIata);
            return ResponseEntity.ok(flights);
        } else {
            return ResponseEntity.badRequest().body(null);
        }
    }
}

