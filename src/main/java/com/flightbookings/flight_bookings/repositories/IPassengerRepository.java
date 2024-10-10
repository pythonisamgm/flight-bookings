package com.flightbookings.flight_bookings.repositories;

import com.flightbookings.flight_bookings.models.Passenger;
import org.springframework.data.jpa.repository.JpaRepository;
/**
 * Repository interface for managing {@link Passenger} entities.
 * Provides basic CRUD operations through JpaRepository.
 */
public interface IPassengerRepository extends JpaRepository<Passenger, Long> {
}
