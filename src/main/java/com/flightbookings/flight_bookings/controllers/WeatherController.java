package com.flightbookings.flight_bookings.controllers;

import com.flightbookings.flight_bookings.models.Weather;
import com.flightbookings.flight_bookings.services.WeatherServiceImpl;
import org.springframework.web.bind.annotation.*;

@CrossOrigin("*")
@RestController
@RequestMapping("api/v1/weather")
public class WeatherController {

    private final WeatherServiceImpl weatherService;

    public WeatherController(WeatherServiceImpl weatherService) {
        this.weatherService = weatherService;
    }

    @PostMapping
    public Weather getWeather (@RequestParam String city, @RequestParam double lat, @RequestParam double lon){
        return weatherService.fetchAndSaveWeather(city, lat, lon);
    }
}
