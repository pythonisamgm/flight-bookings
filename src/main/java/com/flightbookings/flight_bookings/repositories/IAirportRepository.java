package com.flightbookings.flight_bookings.repositories;

import com.flightbookings.flight_bookings.models.Airport;
import org.springframework.data.jpa.repository.JpaRepository;
/**
 * Repository interface for managing {@link Airport} entities.
 * Provides basic CRUD operations through JpaRepository.
 */
public interface IAirportRepository extends JpaRepository<Airport, String> {
}

