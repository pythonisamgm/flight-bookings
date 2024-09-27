package com.flightbookings.flight_bookings.data;

import com.flightbookings.flight_bookings.models.Airport;
import com.flightbookings.flight_bookings.services.interfaces.AirportService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
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


        airports.add(new Airport(null, "MAD", "Madrid-Barajas Adolfo Suárez", "Madrid", "España"));
        airports.add(new Airport(null, "BCN", "Barcelona-El Prat", "Barcelona", "España"));
        airports.add(new Airport(null, "VLC", "Valencia", "Valencia", "España"));
        airports.add(new Airport(null, "AGP", "Malaga-Costa del Sol", "Málaga", "España"));
        airports.add(new Airport(null, "ALC", "Alicante-Elche", "Alicante", "España"));
        airports.add(new Airport(null, "SVQ", "Sevilla", "Sevilla", "España"));
        airports.add(new Airport(null, "BIO", "Bilbao", "Bilbao", "España"));
        airports.add(new Airport(null, "PMI", "Palma de Mallorca", "Palma de Mallorca", "España"));
        airports.add(new Airport(null, "LPA", "Gran Canaria", "Las Palmas", "España"));
        airports.add(new Airport(null, "TFS", "Tenerife Sur", "Tenerife", "España"));


        airports.add(new Airport(null, "LAX", "Los Angeles International Airport", "Los Ángeles", "EE.UU."));
        airports.add(new Airport(null, "JFK", "John F. Kennedy International Airport", "Nueva York", "EE.UU."));
        airports.add(new Airport(null, "ORD", "O'Hare International Airport", "Chicago", "EE.UU."));
        airports.add(new Airport(null, "ATL", "Hartsfield-Jackson Atlanta International Airport", "Atlanta", "EE.UU."));
        airports.add(new Airport(null, "DFW", "Dallas/Fort Worth International Airport", "Dallas", "EE.UU."));
        airports.add(new Airport(null, "YYZ", "Toronto Pearson International Airport", "Toronto", "Canadá"));
        airports.add(new Airport(null, "GRU", "São Paulo/Guarulhos International Airport", "São Paulo", "Brasil"));
        airports.add(new Airport(null, "MEX", "Mexico City International Airport", "Ciudad de México", "México"));
        airports.add(new Airport(null, "BOG", "Bogotá El Dorado International Airport", "Bogotá", "Colombia"));
        airports.add(new Airport(null, "CUN", "Cancún International Airport", "Cancún", "México"));


        airports.add(new Airport(null, "LHR", "London Heathrow Airport", "Londres", "Reino Unido"));
        airports.add(new Airport(null, "AMS", "Amsterdam Airport Schiphol", "Ámsterdam", "Países Bajos"));
        airports.add(new Airport(null, "CDG", "Charles de Gaulle Airport", "París", "Francia"));
        airports.add(new Airport(null, "FRA", "Frankfurt Airport", "Fráncfort", "Alemania"));
        airports.add(new Airport(null, "MUC", "Munich Airport", "Múnich", "Alemania"));
        airports.add(new Airport(null, "ZRH", "Zurich Airport", "Zúrich", "Suiza"));
        airports.add(new Airport(null, "CPH", "Copenhagen Airport", "Copenhague", "Dinamarca"));
        airports.add(new Airport(null, "VIE", "Vienna International Airport", "Viena", "Austria"));
        airports.add(new Airport(null, "LIS", "Lisbon Portela Airport", "Lisboa", "Portugal"));
        airports.add(new Airport(null, "BRU", "Brussels Airport", "Bruselas", "Bélgica"));


        airportService.createAirports(airports);
    }
}
