package com.flightbookings.flight_bookings.dtos.DTOFlight;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Schema(description = "Data Transfer Object for Flight.")
public class FlightDTO {

    @Schema(description = "The database generated flight ID")
    private Long flightId;

    @NotNull(message = "Number of rows is mandatory")
    @Min(value = 1, message = "Number of rows must be at least 1")
    @Schema(description = "Number of rows in the airplane")
    private int numRows;

    @NotNull(message = "Flight number is mandatory")
    @Min(value = 1, message = "Flight number must be at least 1")
    @Schema(description = "Flight number")
    private int flightNumber;

    @NotNull(message = "Departure time is mandatory")
    @Schema(description = "Time of departure")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy HH:mm:ss")
    private LocalDateTime departureTime;

    @NotNull(message = "Arrival time is mandatory")
    @Schema(description = "Time of arrival")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy HH:mm:ss")
    private LocalDateTime arrivalTime;

    @NotBlank(message = "Airplane type is mandatory")
    @Schema(description = "Type of airplane used for the flight")
    private String flightAirplane;

    @NotNull(message = "Capacity of the airplane is mandatory")
    @Min(value = 1, message = "Capacity must be at least 1")
    @Schema(description = "Capacity of the airplane")
    private int capacityPlane;

    @Schema(description = "Availability status of the flight")
    private boolean availability;

    @NotNull(message = "Price of the flight is mandatory")
    @Schema(description = "Price of the flight")
    private BigDecimal flightPrice;

    @Schema(description = "List of seats associated with this flight.")
    private List<Long> seatIds;

    @Schema(description = "List of bookings associated with this flight.")
    private List<Long> bookingIds;

    public FlightDTO() {}

    public Long getFlightId() {
        return flightId;
    }

    public void setFlightId(Long flightId) {
        this.flightId = flightId;
    }

    public int getNumRows() {
        return numRows;
    }

    public void setNumRows(int numRows) {
        this.numRows = numRows;
    }

    public int getFlightNumber() {
        return flightNumber;
    }

    public void setFlightNumber(int flightNumber) {
        this.flightNumber = flightNumber;
    }

    public LocalDateTime getDepartureTime() {
        return departureTime;
    }

    public void setDepartureTime(LocalDateTime departureTime) {
        this.departureTime = departureTime;
    }

    public LocalDateTime getArrivalTime() {
        return arrivalTime;
    }

    public void setArrivalTime(LocalDateTime arrivalTime) {
        this.arrivalTime = arrivalTime;
    }

    public String getFlightAirplane() {
        return flightAirplane;
    }

    public void setFlightAirplane(String flightAirplane) {
        this.flightAirplane = flightAirplane;
    }

    public int getCapacityPlane() {
        return capacityPlane;
    }

    public void setCapacityPlane(int capacityPlane) {
        this.capacityPlane = capacityPlane;
    }

    public boolean isAvailability() {
        return availability;
    }

    public void setAvailability(boolean availability) {
        this.availability = availability;
    }

    public BigDecimal getFlightPrice() {
        return flightPrice;
    }

    public void setFlightPrice(BigDecimal flightPrice) {
        this.flightPrice = flightPrice;
    }

    public List<Long> getSeatIds() {
        return seatIds;
    }

    public void setSeatIds(List<Long> seatIds) {
        this.seatIds = seatIds;
    }

    public List<Long> getBookingIds() {
        return bookingIds;
    }

    public void setBookingIds(List<Long> bookingIds) {
        this.bookingIds = bookingIds;
    }
}
