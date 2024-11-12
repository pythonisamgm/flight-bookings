package com.flightbookings.flight_bookings.services.interfaces;

import com.flightbookings.flight_bookings.models.BookingEntity;
import com.flightbookings.flight_bookings.models.UserEntity;

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

    BookingEntity createBooking(Long flightId, Long passengerId, String seatName, Long userId);

    /**
     * Retrieves a booking by its ID.
     *
     * @param id   the ID of the booking.
     * @param user the user requesting the booking details.
     * @return the Booking object if found, null otherwise.
     */
    BookingEntity getBookingByIdByUser(Long id, UserEntity user);

    /**
     * Retrieves all bookings for a specific user.
     *
     * @param user the user whose bookings are to be retrieved.
     * @return a list of Booking objects for the user.
     */
    List<BookingEntity> getAllBookingsByUser(UserEntity user);
    /**
     * Retrieves all bookings.
     *
     * @return a list of Booking objects.
     */
    List<BookingEntity> getAllBookings();


    /**
     * Updates an existing booking with new details.
     *
     * @param updatedBooking the updated Booking object containing the new details.
     * @return the updated Booking object.
     */
    BookingEntity updateBooking(BookingEntity updatedBooking);

    /**
     * Deletes a booking by its ID.
     *
     * @param id the ID of the booking to delete.
     * @return true if the booking was successfully deleted, false otherwise.
     */
    boolean deleteBooking(Long id);
}
