package com.flightbookings.flight_bookings.models;


import jakarta.persistence.*;

import java.util.Set;

@Entity
@Table (name = "Airport")
public class Airport {

    @Id
    @Column(name = "airport_code", length = 3, nullable = false)
    private String airportCode;

    @Column(name = "airport_name", nullable = false)
    private String airportName;

    @Column(name = "city", nullable = false)
    private String airportCity;

    @Enumerated(EnumType.STRING)
    @Column(name = "country", nullable = false)
    private ECountry airportCountry;


    public Airport(String airportCode, String airportName, String airportCity, ECountry airportCountry) {
        this.airportCode = airportCode;
        this.airportName = airportName;
        this.airportCity = airportCity;
        this.airportCountry = airportCountry;
    }

    // Constructor vacío
    public Airport() {
    }

    // Getters y Setters
    public String getAirportCode() {
        return airportCode;
    }

    public void setAirportCode(String airportCode) {
        this.airportCode = airportCode;
    }

    public String getAirportName() {
        return airportName;
    }

    public void setAirportName(String airportName) {
        this.airportName = airportName;
    }

    public String getAirportCity() {
        return airportCity;
    }

    public void setAirportCity(String airportCity) {
        this.airportCity = airportCity;
    }

    public ECountry getAirportCountry() { // Cambiado a ECountry
        return airportCountry;
    }

    public void setAirportCountry(ECountry airportCountry) { // Cambiado a ECountry
        this.airportCountry = airportCountry;
    }
}