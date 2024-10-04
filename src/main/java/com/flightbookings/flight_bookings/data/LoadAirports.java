package com.flightbookings.flight_bookings.data;

import com.flightbookings.flight_bookings.models.Airport;
import com.flightbookings.flight_bookings.models.EAirport;
import com.flightbookings.flight_bookings.services.interfaces.AirportService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.Set;

@Component
public class LoadAirports implements CommandLineRunner {
    private final AirportService airportService;

    // Cambiar esta variable a false para inhabilitar la carga
    private static final boolean ENABLE_DATA_LOADING = false;

    public LoadAirports(AirportService airportService) {
        this.airportService = airportService;
    }

    @Override
    public void run(String... args) throws Exception {
        loadAirports();
    }

    private void loadAirports() {
        Set<Airport> airports = new HashSet<>();

        // Carga de aeropuertos usando el enum
        for (EAirport airportEnum : EAirport.values()) {
            airports.add(new Airport(airportEnum.getAirportCode(),
                    airportEnum.getAirportName(),
                    airportEnum.getAirportCity(),
                    airportEnum.getAirportCountry()
            ));
        }

        // Crea los aeropuertos en la base de datos
        airportService.createAirports(airports);
    }
}