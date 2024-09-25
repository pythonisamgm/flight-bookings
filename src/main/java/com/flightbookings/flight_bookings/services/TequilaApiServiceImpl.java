package com.flightbookings.flight_bookings.services;

import com.fasterxml.jackson.databind.JsonNode;
import com.flightbookings.flight_bookings.services.interfaces.TequilaApiService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class TequilaApiServiceImpl implements TequilaApiService {

    @Value("${tequila.api.key}")
    private String apiKey;

    private static final String BASE_URL = "https://api.tequila.kiwi.com/v2/";

    private final RestTemplate restTemplate;

    public TequilaApiServiceImpl(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @Override
    public String getIataCodeForCity(String cityName) {
        String url = BASE_URL + "locations/query?term=" + cityName + "&location_types=city&limit=1";
        JsonNode rootNode = restTemplate.getForObject(url, JsonNode.class, apiKey);

        if (rootNode != null && rootNode.has("locations") && rootNode.get("locations").isArray() && rootNode.get("locations").size() > 0) {
            return rootNode.get("locations").get(0).get("code").asText();
        }
        return "";
    }

    @Override
    public JsonNode searchFlights(String originIata, String destinationIata, String dateFrom, String dateTo) {
        String url = BASE_URL + "search?fly_from=" + originIata + "&fly_to=" + destinationIata +
                "&date_from=" + dateFrom + "&date_to=" + dateTo + "&apikey=" + apiKey;
        return restTemplate.getForObject(url, JsonNode.class);
    }
}
