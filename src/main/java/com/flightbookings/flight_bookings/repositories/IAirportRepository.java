package com.flightbookings.flight_bookings.repositories;

import com.flightbookings.flight_bookings.models.AirportEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repository interface for managing {@link AirportEntity} entities.
 * Provides basic CRUD operations through JpaRepository.
 */
@Repository
public interface IAirportRepository extends JpaRepository<AirportEntity, String> {
}

