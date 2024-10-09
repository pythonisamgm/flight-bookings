package com.flightbookings.flight_bookings.dtos.DTOAirport;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

/**
 * Data Transfer Object for Airport, representing the details of an airport in the system.
 */
@Schema(description = "Data Transfer Object for Airport.")
public class AirportDTO {

    @Schema(description = "The airport code")
    @NotNull(message = "Airport code cannot be null")
    @Size(min = 3, max = 3, message = "Airport code must be exactly 3 characters long")
    private String airportCode;

    @Schema(description = "The name of the airport")
    @NotNull(message = "Airport name cannot be null")
    private String airportName;

    @Schema(description = "The city where the airport is located")
    @NotNull(message = "Airport city cannot be null")
    private String airportCity;

    @Schema(description = "The country where the airport is located")
    @NotNull(message = "Airport country cannot be null")
    private String airportCountry;

    public AirportDTO() {}

    /**
     * Gets the airport code.
     *
     * @return the airport code
     */
    public String getAirportCode() {
        return airportCode;
    }

    /**
     * Sets the airport code.
     *
     * @param airportCode the airport code to set
     */
    public void setAirportCode(String airportCode) {
        this.airportCode = airportCode;
    }

    /**
     * Gets the name of the airport.
     *
     * @return the airport name
     */
    public String getAirportName() {
        return airportName;
    }

    /**
     * Sets the name of the airport.
     *
     * @param airportName the airport name to set
     */
    public void setAirportName(String airportName) {
        this.airportName = airportName;
    }

    /**
     * Gets the city where the airport is located.
     *
     * @return the airport city
     */
    public String getAirportCity() {
        return airportCity;
    }

    /**
     * Sets the city where the airport is located.
     *
     * @param airportCity the airport city to set
     */
    public void setAirportCity(String airportCity) {
        this.airportCity = airportCity;
    }

    /**
     * Gets the country where the airport is located.
     *
     * @return the airport country
     */
    public String getAirportCountry() {
        return airportCountry;
    }

    /**
     * Sets the country where the airport is located.
     *
     * @param airportCountry the airport country to set
     */
    public void setAirportCountry(String airportCountry) {
        this.airportCountry = airportCountry;
    }
}
