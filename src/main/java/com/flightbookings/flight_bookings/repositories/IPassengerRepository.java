package com.flightbookings.flight_bookings.repositories;

import com.flightbookings.flight_bookings.models.PassengerEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repository interface for managing {@link PassengerEntity} entities.
 * Provides basic CRUD operations through JpaRepository.
 */
@Repository
public interface IPassengerRepository extends JpaRepository<PassengerEntity, Long> {
}
