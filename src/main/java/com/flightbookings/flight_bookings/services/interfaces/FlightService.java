package com.flightbookings.flight_bookings.services.interfaces;

import com.flightbookings.flight_bookings.models.EFlightAirplane;
import com.flightbookings.flight_bookings.models.Flight;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Interface for managing flight operations in the flight booking system.
 */
public interface FlightService {

   /**
    * Creates a new flight.
    *
    * @param flight the Flight object to be created.
    * @return the created Flight object.
    */
   @Transactional
   Flight createFlight(Flight flight);

    /**
     * Retrieves a flight by its ID.
     *
     * @param id the ID of the flight to retrieve.
     * @return the Flight object if found, null otherwise.
     */

    Flight getFlightById(Long id);

    /**
     * Retrieves all flights.
     *
     * @return a list of all Flight objects.
     */

    List<Flight> getAllFlights();

    /**
     * Updates the details of an existing flight.
     *
     * @param id            the ID of the flight to update.
     * @param flightDetails the updated Flight object.
     * @return the updated Flight object.
     */

    Flight updateFlight(Long id, Flight flightDetails);

    /**
     * Deletes a flight by its ID.
     *
     * @param id the ID of the flight to delete.
     * @return true if the flight was successfully deleted, false otherwise.
     */

    boolean deleteFlight(Long id);




    /**
     * Cancels a flight.
     *
     * @param id the ID of the flight to cancel.
     * @return
     */

    void cancelFlight(Long id);

    /**
     * Delays a flight by updating its departure time.
     *
     * @param id            the ID of the flight to delay.
     * @param newDepartureTime the new departure time for the flight.
     * @return
     */

    void delayFlight(Long id, LocalDateTime newDepartureTime);

    /**
     * Updates the availability status of all flights.
     */

    List<Flight> getFlightsByAirplaneType(EFlightAirplane airplaneType);

    /**
     * Retrieves flights by the type of airplane.
     *
     * @param airplaneType the type of airplane for the flights to retrieve.
     * @return a list of Flight objects that match the airplane type.
     */

    void updateFlightAvailability();
}
