package com.flightbookings.flight_bookings.services.interfaces;

import com.flightbookings.flight_bookings.models.Booking;
import com.flightbookings.flight_bookings.models.User;

import java.util.List;

/**
 * Interface for managing booking operations in the flight booking system.
 */
public interface BookingService {

    /**
     * Creates a new booking for a flight.
     *
     * @param flightId   the ID of the flight to book.
     * @param passengerId the ID of the passenger for whom the booking is being created.
     * @param seatName   the name of the seat to reserve.
     * @param userId       the user making the booking.
     * @return the created Booking object.
     */

    Booking createBooking(Long flightId, Long passengerId, String seatName, Long userId);

    /**
     * Updates an existing booking with new details.
     *
     * @param updatedBooking the updated Booking object containing the new details.
     * @return the updated Booking object.
     */
    Booking updateBooking(Booking updatedBooking);

    /**
     * Creates a booking from a provided Booking object.
     *
     * @param booking the Booking object to be created.
     * @return the created Booking object.
     */
    Booking createBooking2(Booking booking);

    /**
     * Retrieves a booking by its ID.
     *
     * @param id   the ID of the booking.
     * @param user the user requesting the booking details.
     * @return the Booking object if found, null otherwise.
     */
    Booking getBookingByIdByUser(Long id, User user);



    /**
     * Retrieves all bookings for a specific user.
     *
     * @param user the user whose bookings are to be retrieved.
     * @return a list of Booking objects for the user.
     */
    List<Booking> getAllBookingsByUser(User user);
    /**
     * Retrieves all bookings.
     *
     * @return a list of Booking objects.
     */
    List<Booking> getAllBookings();

    /**
     * Updates a booking by its ID.
     *
     * @param id             the ID of the booking to update.
     * @param bookingDetails the updated Booking details.
     * @return the updated Booking object.
     */
    Booking updateBooking2(Long id, Booking bookingDetails);

    Booking updateBooking2(Long id, Booking bookingDetails, Long userId);

    /**
     * Deletes a booking by its ID.
     *
     * @param id the ID of the booking to delete.
     * @return true if the booking was successfully deleted, false otherwise.
     */
    boolean deleteBooking(Long id);
}
