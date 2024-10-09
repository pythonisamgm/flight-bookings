package com.flightbookings.flight_bookings.services.interfaces;

import com.flightbookings.flight_bookings.models.Airport;

import java.util.List;
import java.util.Optional;
import java.util.Set;

public interface AirportService {

    Airport createAirport(Airport airport);

    List<Airport> createAirports(Set<Airport> airports);

    List<Airport> getAllAirports();

    Optional<Airport> getAirportById(String id);


}
