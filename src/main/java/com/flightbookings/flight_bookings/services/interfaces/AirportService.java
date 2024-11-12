package com.flightbookings.flight_bookings.services.interfaces;

import com.flightbookings.flight_bookings.models.AirportEntity;

import java.util.List;
import java.util.Optional;
import java.util.Set;
/**
 * Interface for managing airport operations in the flight booking system.
 */
public interface AirportService {
    /**
     * Creates a new airport.
     *
     * @param airport the Airport object to be created.
     * @return the created Airport object.
     */
    AirportEntity createAirport(AirportEntity airport);
    /**
     * Creates multiple airports from a set of Airport objects.
     *
     * @param airports the set of Airport objects to be created.
     * @return a list of created Airport objects.
     */
    List<AirportEntity> createAirports(Set<AirportEntity> airports); // Método para crear múltiples aeropuertos

    /**
     * Retrieves all airports.
     *
     * @return a list of all Airport objects.
     */
    List<AirportEntity> getAllAirports();
    /**
     * Retrieves an airport by its ID.
     *
     * @param id the ID of the airport to retrieve.
     * @return an Optional containing the Airport object if found, or empty if not found.
     */
    Optional<AirportEntity> getAirportById(String id);


}
