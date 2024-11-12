package com.flightbookings.flight_bookings.repositories;

import com.flightbookings.flight_bookings.models.AirportEntity;
import org.springframework.data.jpa.repository.JpaRepository;
/**
 * Repository interface for managing {@link AirportEntity} entities.
 * Provides basic CRUD operations through JpaRepository.
 */
public interface IAirportRepository extends JpaRepository<AirportEntity, String> {
}

