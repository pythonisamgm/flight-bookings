package com.flightbookings.flight_bookings.models;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;

/**
 * Represents an airport entity in the system.
 * Contains details about the airport's code, name, location, and associated flights.
 */
@Entity
@Table(name = "Airport")
@Schema(description = "All details about the Airport entity.")
public class Airport {

    /** The airport code. */
    @Id
    @Column(name = "airport_code", length = 3, nullable = false)
    @Schema(description = "The airport code")
    private String airportCode;

    /** The name of the airport. */
    @Column(name = "airport_name", nullable = false)
    @Schema(description = "The name of the airport")
    private String airportName;

    /** The city where the airport is located. */
    @Column(name = "city", nullable = false)
    @Schema(description = "The city where the airport is located")
    private String airportCity;

    /** The country where the airport is located. */
    @Enumerated(EnumType.STRING)
    @Column(name = "country", nullable = false)
    @Schema(description = "The country where the airport is located")
    private ECountry airportCountry;

    /**
     * Creates an instance of the Airport class.
     *
     * @param airportCode the code of the airport.
     * @param airportName the name of the airport.
     * @param airportCity the city where the airport is located.
     * @param airportCountry the country of the airport.
     */
    public Airport(String airportCode, String airportName, String airportCity, ECountry airportCountry) {
        this.airportCode = airportCode;
        this.airportName = airportName;
        this.airportCity = airportCity;
        this.airportCountry = airportCountry;
    }

    /** Default constructor for Airport. */
    public Airport() {
    }

    /** @return The airport code. */
    public String getAirportCode() {
        return airportCode;
    }

    /** @param airportCode The airport code. */
    public void setAirportCode(String airportCode) {
        this.airportCode = airportCode;
    }

    /** @return The airport name. */
    public String getAirportName() {
        return airportName;
    }

    /** @param airportName The name of the airport. */
    public void setAirportName(String airportName) {
        this.airportName = airportName;
    }

    /** @return The airport city. */
    public String getAirportCity() {
        return airportCity;
    }

    /** @param airportCity The city where the airport is located. */
    public void setAirportCity(String airportCity) {
        this.airportCity = airportCity;
    }

    /** @return The airport country. */
    public ECountry getAirportCountry() {
        return airportCountry;
    }

    /** @param airportCountry The airport country. */
    public void setAirportCountry(ECountry airportCountry) {
        this.airportCountry = airportCountry;
    }
}
