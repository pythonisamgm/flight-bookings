package com.flightbookings.flight_bookings.repositories;

import com.flightbookings.flight_bookings.models.Seat;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ISeatRepository extends JpaRepository<Seat, Long> {
}
