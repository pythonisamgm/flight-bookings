package com.flightbookings.flight_bookings.services.interfaces;

import com.fasterxml.jackson.databind.JsonNode;

public interface TequilaApiService {
    String getIataCodeForCity(String cityName);

    JsonNode searchFlights(String originIata, String destinationIata, String dateFrom, String dateTo);
}
