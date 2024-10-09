package com.flightbookings.flight_bookings.models;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;

import java.util.Set;
/**
 * Represents an airport entity in the system.
 * Contains details about the airport's code, name, location, and associated flights.
 */
@Entity
@Table(name = "Airport")
@Schema(description = "All details about the Airport entity.")
public class Airport {

    /**
     * The airport code.
     */
    @Id
    @Column(name = "airport_code", length = 3, nullable = false)
    @Schema(description = "The airport code")
    private String airportCode;

    /**
     * The name of the airport.
     */
    @Column(name = "airport_name", nullable = false)
    @Schema(description = "The name of the airport")
    private String airportName;

    /**
     * The city where the airport is located.
     */
    @Column(name = "city", nullable = false)
    @Schema(description = "The city where the airport is located")
    private String airportCity;

    /**
     * The country where the airport is located.
     */
    @Enumerated(EnumType.STRING)
    @Column(name = "country", nullable = false)
    @Schema(description = "The country where the airport is located")
    private ECountry airportCountry;

    /**
     * Constructs a new Airport with the specified details.

     * * @param airportCode    The airport code.
     * @param airportName    The name of the airport.
     * @param airportCity    The city where the airport is located.
     * @param airportCountry The country where the airport is located.
     */
    public Airport(String airportCode, String airportName, String airportCity, ECountry airportCountry) {
        this.airportCode = airportCode;
        this.airportName = airportName;
        this.airportCity = airportCity;
        this.airportCountry = airportCountry;
    }

    /**
     * Default constructor for Airport.
     */
    public Airport() {
    }

    /**
     * Gets the airport code.
     *
     * @return The airport code.
     */
    public String getAirportCode() {
        return airportCode;
    }

    /**
     * Sets the airport code.
     *
     * @param airportCode The airport code.
     */
    public void setAirportCode(String airportCode) {
        this.airportCode = airportCode;
    }

    /**
     * Gets the country where the airport is located.
     *
     * @return The airport country.
     */
    public String getAirportName() {
        return airportName;
    }

    /**
     * Sets the name of the airport.
     *
     * @param airportName The name of the airport.
     */
    public void setAirportName(String airportName) {
        this.airportName = airportName;
    }

    /**
     * Gets the city where the airport is located.
     *
     * @return The airport city.
     */
    public String getAirportCity() {
        return airportCity;
    }

    /**
     * Sets the city where the airport is located.
     *
     * @param airportCity The airport city.
     */
    public void setAirportCity(String airportCity) {
        this.airportCity = airportCity;
    }

    /**
     * Gets the country where the airport is located.
     *
     * @return The airport country.
     */
    public ECountry getAirportCountry() { // Cambiado a ECountry
        return airportCountry;
    }

    /**
     * Sets the country where the airport is located.
     *
     * @param airportCountry The airport country.
     */
    public void setAirportCountry(ECountry airportCountry) { // Cambiado a ECountry
        this.airportCountry = airportCountry;
    }
}