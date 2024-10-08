package com.flightbookings.flight_bookings.repositories;

import com.flightbookings.flight_bookings.models.Booking;
import com.flightbookings.flight_bookings.models.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
/**
 * Repository interface for managing {@link Booking} entities.
 * Provides CRUD operations and a method to find bookings by user.
 */
public interface IBookingRepository extends JpaRepository<Booking, Long> {
    /**
     * Finds a list of bookings associated with a given user.
     *
     * @param user the user whose bookings are to be found.
     * @return a list of bookings for the specified user.
     */
    List<Booking> findByUser(User user);
}
