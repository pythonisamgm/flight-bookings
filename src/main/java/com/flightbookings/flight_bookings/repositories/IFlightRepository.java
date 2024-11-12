package com.flightbookings.flight_bookings.repositories;

import com.flightbookings.flight_bookings.models.FlightEntity;
import org.springframework.data.jpa.repository.JpaRepository;
/**
 * Repository interface for managing {@link FlightEntity} entities.
 * Provides basic CRUD operations through JpaRepository.
 */
public interface IFlightRepository extends JpaRepository<FlightEntity, Long> {
}
