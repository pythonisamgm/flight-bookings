package com.flightbookings.flight_bookings.controllers;

import com.fasterxml.jackson.databind.JsonNode;
import com.flightbookings.flight_bookings.services.interfaces.TequilaApiService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin("*")
@RestController
public class TequilaApiController {

    private final TequilaApiService tequilaApiService;

    public TequilaApiController(TequilaApiService tequilaApiService) {
        this.tequilaApiService = tequilaApiService;
    }

    @GetMapping("/flights")
    public ResponseEntity<JsonNode> getFlights(@RequestParam String originCity,
                                               @RequestParam String destinationCity,
                                               @RequestParam String dateFrom,
                                               @RequestParam String dateTo) {
        String originIata = tequilaApiService.getIataCodeForCity(originCity);
        String destinationIata = tequilaApiService.getIataCodeForCity(destinationCity);

        if (!originIata.isEmpty() && !destinationIata.isEmpty()) {
            JsonNode flights = tequilaApiService.searchFlights(originIata, destinationIata, dateFrom, dateTo);
            return ResponseEntity.ok(flights);
        } else {
            return ResponseEntity.badRequest().body(null);
        }
    }
}

