package com.flightbookings.flight_bookings.repositories;

import com.flightbookings.flight_bookings.models.Flight;
import com.flightbookings.flight_bookings.models.Seat;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ISeatRepository extends JpaRepository<Seat, Long> {
    Optional<Seat> findByFlightAndSeatName(Flight flight, String seatName);
    List<Seat> findByFlight(Flight flight);
}
