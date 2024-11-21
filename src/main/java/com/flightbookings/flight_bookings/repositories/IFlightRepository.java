package com.flightbookings.flight_bookings.repositories;

import com.flightbookings.flight_bookings.models.FlightEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repository interface for managing {@link FlightEntity} entities.
 * Provides basic CRUD operations through JpaRepository.
 */
@Repository
public interface IFlightRepository extends JpaRepository<FlightEntity, Long> {
}
