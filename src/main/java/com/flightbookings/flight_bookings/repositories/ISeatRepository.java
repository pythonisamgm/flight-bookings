package com.flightbookings.flight_bookings.repositories;

import com.flightbookings.flight_bookings.models.Flight;
import com.flightbookings.flight_bookings.models.Seat;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
/**
 * Repository interface for managing {@link Seat} entities.
 * Provides CRUD operations and methods to find seats by flight and seat name.
 */
public interface ISeatRepository extends JpaRepository<Seat, Long> {
    /**
     * Finds a specific seat by flight and seat name.
     *
     * @param flight the flight associated with the seat.
     * @param seatName the name of the seat to find.
     * @return an optional containing the found seat, or empty if not found.
     */
    Optional<Seat> findByFlightAndSeatName(Flight flight, String seatName);
    /**
     * Finds a list of seats associated with a given flight.
     *
     * @param flight the flight whose seats are to be found.
     * @return a list of seats for the specified flight.
     */
    List<Seat> findByFlight(Flight flight);
}
