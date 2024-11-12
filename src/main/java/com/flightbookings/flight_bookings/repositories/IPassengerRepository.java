package com.flightbookings.flight_bookings.repositories;

import com.flightbookings.flight_bookings.models.PassengerEntity;
import org.springframework.data.jpa.repository.JpaRepository;
/**
 * Repository interface for managing {@link PassengerEntity} entities.
 * Provides basic CRUD operations through JpaRepository.
 */
public interface IPassengerRepository extends JpaRepository<PassengerEntity, Long> {
}
