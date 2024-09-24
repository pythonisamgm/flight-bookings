package com.flightbookings.flight_bookings.repositories;

import com.flightbookings.flight_bookings.models.Booking;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface IBookingRepository extends JpaRepository<Booking, Long> {
    List<Booking> findByPassenger_UserId(Long userId);
}
