package com.flightbookings.flight_bookings.repositories;

import com.flightbookings.flight_bookings.models.Weather;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IWeatherRepository extends JpaRepository<Weather, Long> {
}
