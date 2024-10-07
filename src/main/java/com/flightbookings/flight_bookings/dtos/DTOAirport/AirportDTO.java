package com.flightbookings.flight_bookings.dtos.DTOAirport;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

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
}
