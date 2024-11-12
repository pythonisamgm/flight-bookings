package com.flightbookings.flight_bookings.repositories;

import com.flightbookings.flight_bookings.models.BookingEntity;
import com.flightbookings.flight_bookings.models.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
/**
 * Repository interface for managing {@link BookingEntity} entities.
 * Provides CRUD operations and a method to find bookings by user.
 */
@Repository
public interface IBookingRepository extends JpaRepository<BookingEntity, Long> {
    /**
     * Finds a list of bookings associated with a given user.
     *
     * @param user the user whose bookings are to be found.
     * @return a list of bookings for the specified user.
     */
    List<BookingEntity> findByUser(UserEntity user);
}
