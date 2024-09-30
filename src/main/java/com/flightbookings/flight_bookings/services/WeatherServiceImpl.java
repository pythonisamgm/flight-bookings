package com.flightbookings.flight_bookings.services;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.flightbookings.flight_bookings.models.Weather;
import com.flightbookings.flight_bookings.repositories.IWeatherRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class WeatherServiceImpl {

    private final IWeatherRepository iWeatherRepository;
    private final RestTemplate restTemplate;
    private final ObjectMapper objectMapper;

    private final String URL = "https://api.open-meteo.com/v1/forecast?latitude={lat}&longitude={lon}&current_weather=true";

    public WeatherServiceImpl(IWeatherRepository iWeatherRepository) {
        this.iWeatherRepository = iWeatherRepository;
        this.restTemplate = new RestTemplate();
        this.objectMapper = new ObjectMapper();
    }

    public Weather fetchAndSaveWeather (String city, double lat, double lon){
        String response = restTemplate.getForObject(URL, String.class, lat, lon);

        Weather weather = new Weather();
        try {
            JsonNode jsonNode = objectMapper.readTree(response);
            JsonNode currentWeather = jsonNode.path("current_weather");

            weather.setTemperature(currentWeather.path("temperature").asDouble());
            weather.setWindSpeed(currentWeather.path("windspeed").asDouble());
            weather.setWeatherCondition(currentWeather.path("weathercode").asText());
            weather.setCity(city);
        }catch (Exception e){
            e.printStackTrace();
        }

        return iWeatherRepository.save(weather);
    }
}
