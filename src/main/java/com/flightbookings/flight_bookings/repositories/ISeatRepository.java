package com.flightbookings.flight_bookings.repositories;

import com.flightbookings.flight_bookings.models.ESeatLetter;
import com.flightbookings.flight_bookings.models.Seat;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ISeatRepository extends JpaRepository<Seat, Long> {
    Optional<Seat> fetchSeatData(Long flightId, int row, ESeatLetter seatLetter);
}
