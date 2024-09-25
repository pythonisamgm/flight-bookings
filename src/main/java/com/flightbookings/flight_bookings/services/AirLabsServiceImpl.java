package com.flightbookings.flight_bookings.services;

import com.fasterxml.jackson.databind.JsonNode;
import com.flightbookings.flight_bookings.services.interfaces.AirLabsService;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class AirLabsServiceImpl implements AirLabsService {

    private static final String API_KEY = "YOUR_API_KEY";
    private static final String BASE_URL = "https://airlabs.co/api/v9/";

    private final RestTemplate restTemplate;

    public AirLabsServiceImpl() {
        this.restTemplate = new RestTemplate();
    }

    @Override
    public String getIataCodeForCity(String cityName) {
        String url = BASE_URL + "suggest?q=" + cityName + "&api_key=" + API_KEY;
        JsonNode rootNode = restTemplate.getForObject(url, JsonNode.class);

        if (rootNode != null && rootNode.has("cities") && rootNode.get("cities").isArray() && rootNode.get("cities").size() > 0) {
            return rootNode.get("cities").get(0).get("city_code").asText();
        }
        return "";
    }

    @Override
    public JsonNode searchFlights(String originIata, String destinationIata) {
        String url = BASE_URL + "flights?dep_iata=" + originIata + "&arr_iata=" + destinationIata + "&api_key=" + API_KEY;
        return restTemplate.getForObject(url, JsonNode.class);
    }
}
