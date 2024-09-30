package com.flightbookings.flight_bookings.services.interfaces;

import com.flightbookings.flight_bookings.models.Weather;

public interface WeatherService {
    Weather fetchAndSaveWeather(String city, double lat, double lon);
}
