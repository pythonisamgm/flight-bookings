package com.flightbookings.flight_bookings.data;

import com.flightbookings.flight_bookings.models.Airport;
import com.flightbookings.flight_bookings.services.interfaces.AirportService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.Set;

@Component
public class LoadAirports implements CommandLineRunner {
    private final AirportService airportService;

    public LoadAirports(AirportService airportService) {
        this.airportService = airportService;
    }

    @Override
    public void run(String... args) throws Exception {
        loadAirports();
    }

    private void loadAirports() {
        Set<Airport> airports = new HashSet<>();

        // Carga de aeropuertos
        airports.add(new Airport("MAD", "Madrid-Barajas Adolfo Suárez", "Madrid", "España"));
        airports.add(new Airport("BCN", "Barcelona-El Prat", "Barcelona", "España"));
        airports.add(new Airport("VLC", "Valencia", "Valencia", "España"));
        airports.add(new Airport("AGP", "Malaga-Costa del Sol", "Málaga", "España"));
        airports.add(new Airport("ALC", "Alicante-Elche", "Alicante", "España"));
        airports.add(new Airport("SVQ", "Sevilla", "Sevilla", "España"));
        airports.add(new Airport("BIO", "Bilbao", "Bilbao", "España"));
        airports.add(new Airport("PMI", "Palma de Mallorca", "Palma de Mallorca", "España"));
        airports.add(new Airport("LPA", "Gran Canaria", "Las Palmas", "España"));
        airports.add(new Airport("TFS", "Tenerife Sur", "Tenerife", "España"));

        airports.add(new Airport("LAX", "Los Angeles International Airport", "Los Ángeles", "EE.UU."));
        airports.add(new Airport("JFK", "John F. Kennedy International Airport", "Nueva York", "EE.UU."));
        airports.add(new Airport("ORD", "O'Hare International Airport", "Chicago", "EE.UU."));
        airports.add(new Airport("ATL", "Hartsfield-Jackson Atlanta International Airport", "Atlanta", "EE.UU."));
        airports.add(new Airport("DFW", "Dallas/Fort Worth International Airport", "Dallas", "EE.UU."));
        airports.add(new Airport("YYZ", "Toronto Pearson International Airport", "Toronto", "Canadá"));
        airports.add(new Airport("GRU", "São Paulo/Guarulhos International Airport", "São Paulo", "Brasil"));
        airports.add(new Airport("MEX", "Mexico City International Airport", "Ciudad de México", "México"));
        airports.add(new Airport("BOG", "Bogotá El Dorado International Airport", "Bogotá", "Colombia"));
        airports.add(new Airport("CUN", "Cancún International Airport", "Cancún", "México"));

        airports.add(new Airport("LHR", "London Heathrow Airport", "Londres", "Reino Unido"));
        airports.add(new Airport("AMS", "Amsterdam Airport Schiphol", "Ámsterdam", "Países Bajos"));
        airports.add(new Airport("CDG", "Charles de Gaulle Airport", "París", "Francia"));
        airports.add(new Airport("FRA", "Frankfurt Airport", "Fráncfort", "Alemania"));
        airports.add(new Airport("MUC", "Munich Airport", "Múnich", "Alemania"));
        airports.add(new Airport("ZRH", "Zurich Airport", "Zúrich", "Suiza"));
        airports.add(new Airport("CPH", "Copenhagen Airport", "Copenhague", "Dinamarca"));
        airports.add(new Airport("VIE", "Vienna International Airport", "Viena", "Austria"));
        airports.add(new Airport("LIS", "Lisbon Portela Airport", "Lisboa", "Portugal"));
        airports.add(new Airport("BRU", "Brussels Airport", "Bruselas", "Bélgica"));

        // Crea los aeropuertos en la base de datos
        airportService.createAirports(airports);
    }
}