package com.flightbookings.flight_bookings.repositories;

import com.flightbookings.flight_bookings.models.Passenger;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IPassengerRepository extends JpaRepository<Passenger, Long> {
}
