package com.flightbookings.flight_bookings.services;

import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.Date;

@Service
public class FlightApiService {

    private static final String API_URL = "https://api.tequila.kiwi.com/v2/search";
    private static final String API_KEY = "s-z-3YasBtWow4bAj-RXHyprT50H-1vO";

    public String searchFlights(String from, String to, String dateFrom, String dateTo) throws Exception {

        String formattedDateFrom = formatAndEncodeDate(dateFrom);
        String formattedDateTo = formatAndEncodeDate(dateTo);

        String urlString = API_URL + "?fly_from=" + URLEncoder.encode(from, StandardCharsets.UTF_8.toString()) +
                "&fly_to=" + URLEncoder.encode(to, StandardCharsets.UTF_8.toString()) +
                "&date_from=" + formattedDateFrom +
                "&date_to=" + formattedDateTo +
                "&limit=10";

        //esto se puede quitar luego. Era para ver qué había que poner en postman
        System.out.println("URL construida: " + urlString);

        URL url = new URL(urlString);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");
        conn.setRequestProperty("apikey", API_KEY);

        int responseCode = conn.getResponseCode();
        if (responseCode == HttpURLConnection.HTTP_OK) {
            BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            String inputLine;
            StringBuilder content = new StringBuilder();

            while ((inputLine = in.readLine()) != null) {
                content.append(inputLine);
            }

            in.close();
            conn.disconnect();

            return content.toString();
        } else {
            throw new IOException("Server returned HTTP response code: " + responseCode + " for URL: " + urlString);
        }
    }

    private String formatAndEncodeDate(String date) throws Exception {
        SimpleDateFormat inputFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date parsedDate = inputFormat.parse(date);

        SimpleDateFormat outputFormat = new SimpleDateFormat("dd/MM/yyyy");
        String formattedDate = outputFormat.format(parsedDate);

        return URLEncoder.encode(formattedDate, StandardCharsets.UTF_8.toString());
    }
}
