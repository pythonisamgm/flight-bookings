package com.flightbookings.flight_bookings.dtos.DTOFlight;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
/**
        * Data Transfer Object for Flight.
 */
@Schema(description = "Data Transfer Object for Flight.")
public class FlightDTO {
    /**
     * The unique identifier of the flight.
     */
    @Schema(description = "The database generated flight ID")
    private Long flightId;
    /**
     * The number of rows in the airplane.
     */
    @Schema(description = "Number of rows in the airplane")
    private int numRows;
    /**
     * The flight number.
     */
    @Schema(description = "Flight number")
    private int flightNumber;
    /**
     * The time of departure.
     */
    @Schema(description = "Time of departure")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy HH:mm:ss")
    private LocalDateTime departureTime;
    /**
     * The time of arrival.
     */
    @Schema(description = "Time of arrival")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy HH:mm:ss")
    private LocalDateTime arrivalTime;
    /**
     * The type of airplane used for the flight.
     */
    @Schema(description = "Type of airplane used for the flight")
    private String flightAirplane;
    /**
     * The capacity of the airplane.
     */
    @Schema(description = "Capacity of the airplane")
    private int capacityPlane;
    /**
     * The availability status of the flight.
     */
    @Schema(description = "Availability status of the flight")
    private boolean availability;
    /**
     * The price of the flight.
     */
    @Schema(description = "Price of the flight")
    private BigDecimal flightPrice;
    /**
     * List of seat IDs associated with this flight.
     */
    @Schema(description = "List of seats associated with this flight.")
    private List<Long> seatIds;
    /**
     * List of booking IDs associated with this flight.
     */
    @Schema(description = "List of bookings associated with this flight.")
    private List<Long> bookingIds;

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

