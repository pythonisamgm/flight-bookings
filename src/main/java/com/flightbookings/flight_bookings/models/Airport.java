package com.flightbookings.flight_bookings.models;

import com.flightbookings.flight_bookings.models.Flight;

import java.util.Set;//package com.flightbookings.flight_bookings.models;


import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;

import java.util.Set;

@Entity
@Table (name = "Airport")
@Schema(description = "All details about the Airport entity.")
public class Airport {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = "The database generated airport ID", example = "1")
    private Long airportId;

    @Column
    @Schema(description = "Airport code")
    private String airportCode;

    @Column
    @Schema(description = "Airport name")
    private String airportName;

    @Column
    @Schema(description = "City where the airport is located")
    private String airportCity;

    @Column
    @Schema(description = "City where the airport is located")
    private String airportCountry;

    @ManyToMany(
            cascade = CascadeType.ALL,
            fetch = FetchType.LAZY)
    @JoinTable(
            name = "airport_flight",
            joinColumns = @JoinColumn(name = "airport_id"),
            inverseJoinColumns = @JoinColumn(name = "flight_id"))
    @Schema(description = "Flights associated with this airport")
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
}
