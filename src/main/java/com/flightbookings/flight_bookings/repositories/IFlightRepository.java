package com.flightbookings.flight_bookings.repositories;

import com.flightbookings.flight_bookings.models.Flight;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IFlightRepository extends JpaRepository<Flight, Long> {
}
