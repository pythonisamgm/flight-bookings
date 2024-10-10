package com.flightbookings.flight_bookings.services.interfaces;

import com.flightbookings.flight_bookings.models.Passenger;

import java.util.List;

/**
 * Interface for managing passenger operations in the flight booking system.
 */
public interface PassengerService {

    /**
     * Creates a new passenger record.
     *
     * @param passenger the Passenger object to be created.
     * @return the created Passenger object.
     */
    Passenger createPassenger(Passenger passenger);

    /**
     * Retrieves a passenger by their ID.
     *
     * @param id the ID of the passenger to retrieve.
     * @return the Passenger object if found, null otherwise.
     */
    Passenger getPassengerById(Long id);

    /**
     * Retrieves all passengers.
     *
     * @return a list of all Passenger objects.
     */
    List<Passenger> getAllPassengers();

    /**
     * Updates a passenger's details.
     *
     * @param id              the ID of the passenger to update.
     * @param passengerDetails the updated Passenger details.
     * @return the updated Passenger object.
     */
    Passenger updatePassenger(Long id, Passenger passengerDetails);

    /**
     * Deletes a passenger by their ID.
     *
     * @param id the ID of the passenger to delete.
     * @return true if the passenger was successfully deleted, false otherwise.
     */
    boolean deletePassenger(Long id);
}
