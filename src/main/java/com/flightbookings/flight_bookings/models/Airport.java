package com.flightbookings.flight_bookings.models;


import jakarta.persistence.*;

import java.util.Set;

@Entity
@Table (name = "Airport")
public class Airport {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long airportId;

    @Column
    private String airportCode;

    @Column
    private String airportName;

    @Column
    private String airportCity;

    @Column
    private String airportCountry;

    @ManyToMany(
            cascade = CascadeType.ALL,
            fetch = FetchType.LAZY)
    @JoinTable(
            name = "airport_flight",
            joinColumns = @JoinColumn(name = "airport_id"),
            inverseJoinColumns = @JoinColumn(name = "flight_id"))
    private Set<Flight> flight;

    public Airport(Long airportId, String airportCode, String airportName, String airportCity, String airportCountry) {
        this.airportId = airportId;
        this.airportCode = airportCode;
        this.airportName = airportName;
        this.airportCity = airportCity;
        this.airportCountry = airportCountry;
    }

    public Airport() {
    }

    public Long getAirportId() {
        return airportId;
    }

    public void setAirportId(Long airportId) {
        this.airportId = airportId;
    }

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

    public String getAirportCountry() {
        return airportCountry;
    }

    public void setAirportCountry(String airportCountry) {
        this.airportCountry = airportCountry;
    }
    public Set<Flight> getFlight() {
        return flight;
    }

    public void setFlight(Set<Flight> flight) {
        this.flight = flight;
    }
}       return airportCity;
//    }
//
//    public void setAirportCity(String airportCity) {
//        this.airportCity = airportCity;
//    }
//
//    public String getAirportCountry() {
//        return airportCountry;
//    }
//
//    public void setAirportCountry(String airportCountry) {
//        this.airportCountry = airportCountry;
//    }
//    public Set<Flight> getFlight() {
//        return flight;
//    }
//
//    public void setFlight(Set<Flight> flight) {
//        this.flight = flight;
//    }
//}
