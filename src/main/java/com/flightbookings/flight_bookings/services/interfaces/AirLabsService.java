package com.flightbookings.flight_bookings.services.interfaces;

import com.fasterxml.jackson.databind.JsonNode;

public interface AirLabsService {
    String getIataCodeForCity(String cityName);

    JsonNode searchFlights(String originIata, String destinationIata);
}
