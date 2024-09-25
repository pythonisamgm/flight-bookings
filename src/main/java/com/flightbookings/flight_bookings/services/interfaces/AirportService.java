package com.flightbookings.flight_bookings.services.interfaces;

import com.flightbookings.flight_bookings.models.Airport;

import java.util.List;
import java.util.Optional;

public interface AirportService {
    Airport createAirport(Airport airport);

    Airport updateAirport(Airport airport, Long id);

    List<Airport> getAllAirports();

    Optional<Airport> getAirportById(Long id);


}
