package com.flightbookings.flight_bookings.services.interfaces;

import com.flightbookings.flight_bookings.models.FlightEntity;
import com.flightbookings.flight_bookings.models.SeatEntity;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * Interface for managing seat operations in the flight booking system.
 */
public interface SeatService {

    /**
     * Retrieves a seat by its ID.
     *
     * @param seatId the ID of the seat to retrieve.
     * @return an Optional containing the Seat object if found, or an empty Optional if not found.
     */
    Optional<SeatEntity> getSeatById(Long seatId);

    /**
     * Initializes seats for a given flight.
     *
     * @param flight  the flight for which seats are being initialized.
     * @param numRows the number of rows in the flight.
     * @return a list of seat identifiers that were initialized.
     */
    @Transactional
    List<String> initializeSeats(FlightEntity flight, int numRows);

    /**
     * Initializes seats for all flights in the system.
     */
    @Transactional
    void initializeSeatsForAllFlights();

    /**
     * Reserves a seat for a given flight.
     *
     * @param flight    the flight for which the seat is being reserved.
     * @param seatName  the name of the seat to reserve.
     * @return the reserved Seat object.
     */
    @Transactional
    SeatEntity reserveSeat(FlightEntity flight, String seatName);
}
