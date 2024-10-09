package com.flightbookings.flight_bookings.data;

import com.flightbookings.flight_bookings.models.Airport;
import com.flightbookings.flight_bookings.models.EAirport;
import com.flightbookings.flight_bookings.services.interfaces.AirportService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.Set;
/**
 * Component to load airports into the database on application startup.
 */
@Component
public class LoadAirports implements CommandLineRunner {
    private final AirportService airportService;
    /**
     * Constructor to initialize LoadAirports with AirportService.
     *
     * @param airportService the service for airport management.
     */
    public LoadAirports(AirportService airportService) {
        this.airportService = airportService;
    }

    @Override
    public void run(String... args) throws Exception {
        loadAirports();
    }
    /**
     * Loads airports from the EAirport enum and saves them to the database.
     */
    private void loadAirports() {
        Set<Airport> airports = new HashSet<>();

        for (EAirport airportEnum : EAirport.values()) {
            airports.add(new Airport(airportEnum.getAirportCode(),
                    airportEnum.getAirportName(),
                    airportEnum.getAirportCity(),
                    airportEnum.getAirportCountry()
            ));
        }

        airportService.createAirports(airports);
    }
}