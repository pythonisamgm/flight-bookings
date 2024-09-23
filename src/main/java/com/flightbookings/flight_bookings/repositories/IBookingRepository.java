package com.flightbookings.flight_bookings.repositories;

import com.flightbookings.flight_bookings.models.Booking;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IBookingRepository extends JpaRepository<Booking, Long> {
}
