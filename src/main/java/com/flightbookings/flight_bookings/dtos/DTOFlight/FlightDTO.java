package com.flightbookings.flight_bookings.dtos.DTOFlight;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.flightbookings.flight_bookings.models.EFlightAirplane;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;

/**
 * Data Transfer Object for Flight, providing a simplified version of the Flight entity for client-server communication.
 */
@Schema(description = "Data Transfer Object for Flight.")
public class FlightDTO {

    @Schema(description = "The database generated flight ID")
    private Long flightId;

    @NotNull(message = "Flight number is mandatory")
    @Min(value = 1, message = "Flight number must be at least 1")
    @Schema(description = "Flight number")
    private int flightNumber;

    @NotNull(message = "Number of rows is mandatory")
    @Min(value = 1, message = "Number of rows must be at least 1")
    @Schema(description = "Number of rows in the airplane")
    private int numRows;

    @NotNull(message = "Departure time is mandatory")
    @Schema(description = "Time of departure")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy HH:mm:ss")
    private LocalDateTime departureTime;

    @NotNull(message = "Arrival time is mandatory")
    @Schema(description = "Time of arrival")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy HH:mm:ss")
    private LocalDateTime arrivalTime;

    @Schema(description = "Duration of the flight in ISO-8601 format (e.g., PT2H for 2 hours)")
    private Duration flightDuration;

    @NotNull(message = "Airplane type is mandatory")
    @Schema(description = "Type of airplane used for the flight")
    private EFlightAirplane flightAirplane;

    @NotNull(message = "Capacity of the airplane is mandatory")
    @Min(value = 1, message = "Capacity must be at least 1")
    @Schema(description = "Capacity of the airplane")
    private int capacityPlane;

    @Schema(description = "Availability status of the flight")
    private boolean availability;

    @NotNull(message = "Price of the flight is mandatory")
    @Schema(description = "Price of the flight")
    private BigDecimal flightPrice;

    @Schema(description = "List of seat IDs associated with this flight.")
    private List<Long> seatIds;

    @Schema(description = "List of booking IDs associated with this flight.")
    private List<Long> bookingIds;

    @NotNull(message = "Origin airport is mandatory")
    @Schema(description = "ID of the origin airport")
    private Long originAirportId;

    @NotNull(message = "Destination airport is mandatory")
    @Schema(description = "ID of the destination airport")
    private Long destinationAirportId;

    // Default constructor
    public FlightDTO() {}

    // Getters and Setters

    /**
     * Gets the flight ID.
     *
     * @return the flight ID
     */
    public Long getFlightId() {
        return flightId;
    }

    /**
     * Sets the flight ID.
     *
     * @param flightId the flight ID to set
     */
    public void setFlightId(Long flightId) {
        this.flightId = flightId;
    }

    /**
     * Gets the flight number.
     *
     * @return the flight number
     */
    public int getFlightNumber() {
        return flightNumber;
    }

    /**
     * Sets the flight number.
     *
     * @param flightNumber the flight number to set
     */
    public void setFlightNumber(int flightNumber) {
        this.flightNumber = flightNumber;
    }

    /**
     * Gets the number of rows in the airplane.
     *
     * @return the number of rows
     */
    public int getNumRows() {
        return numRows;
    }

    /**
     * Sets the number of rows in the airplane.
     *
     * @param numRows the number of rows to set
     */
    public void setNumRows(int numRows) {
        this.numRows = numRows;
    }

    /**
     * Gets the departure time of the flight.
     *
     * @return the departure time
     */
    public LocalDateTime getDepartureTime() {
        return departureTime;
    }

    /**
     * Sets the departure time of the flight.
     *
     * @param departureTime the departure time to set
     */
    public void setDepartureTime(LocalDateTime departureTime) {
        this.departureTime = departureTime;
    }

    /**
     * Gets the arrival time of the flight.
     *
     * @return the arrival time
     */
    public LocalDateTime getArrivalTime() {
        return arrivalTime;
    }

    /**
     * Sets the arrival time of the flight.
     *
     * @param arrivalTime the arrival time to set
     */
    public void setArrivalTime(LocalDateTime arrivalTime) {
        this.arrivalTime = arrivalTime;
    }

    /**
     * Gets the flight duration.
     *
     * @return the flight duration
     */
    public Duration getFlightDuration() {
        return flightDuration;
    }

    /**
     * Sets the flight duration.
     *
     * @param flightDuration the flight duration to set
     */
    public void setFlightDuration(Duration flightDuration) {
        this.flightDuration = flightDuration;
    }

    /**
     * Gets the type of airplane used for the flight.
     *
     * @return the type of airplane
     */
    public EFlightAirplane getFlightAirplane() {
        return flightAirplane;
    }

    /**
     * Sets the type of airplane used for the flight.
     *
     * @param flightAirplane the type of airplane to set
     */
    public void setFlightAirplane(EFlightAirplane flightAirplane) {
        this.flightAirplane = flightAirplane;
    }

    /**
     * Gets the capacity of the airplane.
     *
     * @return the capacity of the airplane
     */
    public int getCapacityPlane() {
        return capacityPlane;
    }

    /**
     * Sets the capacity of the airplane.
     *
     * @param capacityPlane the capacity to set
     */
    public void setCapacityPlane(int capacityPlane) {
        this.capacityPlane = capacityPlane;
    }

    /**
     * Checks if the flight is available.
     *
     * @return true if available, otherwise false
     */
    public boolean isAvailability() {
        return availability;
    }

    /**
     * Sets the availability status of the flight.
     *
     * @param availability the availability status to set
     */
    public void setAvailability(boolean availability) {
        this.availability = availability;
    }

    /**
     * Gets the price of the flight.
     *
     * @return the flight price
     */
    public BigDecimal getFlightPrice() {
        return flightPrice;
    }

    /**
     * Sets the price of the flight.
     *
     * @param flightPrice the flight price to set
     */
    public void setFlightPrice(BigDecimal flightPrice) {
        this.flightPrice = flightPrice;
    }

    /**
     * Gets the list of seat IDs associated with this flight.
     *
     * @return the list of seat IDs
     */
    public List<Long> getSeatIds() {
        return seatIds;
    }

    /**
     * Sets the list of seat IDs associated with this flight.
     *
     * @param seatIds the list of seat IDs to set
     */
    public void setSeatIds(List<Long> seatIds) {
        this.seatIds = seatIds;
    }

    /**
     * Gets the list of booking IDs associated with this flight.
     *
     * @return the list of booking IDs
     */
    public List<Long> getBookingIds() {
        return bookingIds;
    }

    /**
     * Sets the list of booking IDs associated with this flight.
     *
     * @param bookingIds the list of booking IDs to set
     */
    public void setBookingIds(List<Long> bookingIds) {
        this.bookingIds = bookingIds;
    }

    /**
     * Gets the ID of the origin airport.
     *
     * @return the origin airport ID
     */
    public Long getOriginAirportId() {
        return originAirportId;
    }

    /**
     * Sets the ID of the origin airport.
     *
     * @param originAirportId the origin airport ID to set
     */
    public void setOriginAirportId(Long originAirportId) {
        this.originAirportId = originAirportId;
    }

    /**
     * Gets the ID of the destination airport.
     *
     * @return the destination airport ID
     */
    public Long getDestinationAirportId() {
        return destinationAirportId;
    }

    /**
     * Sets the ID of the destination airport.
     *
     * @param destinationAirportId the destination airport ID to set
     */
    public void setDestinationAirportId(Long destinationAirportId) {
        this.destinationAirportId = destinationAirportId;
    }
}
