package com.flightbookings.flight_bookings.models;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;

import java.math.BigDecimal;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "flight")
@Schema(description = "All details about the Flight entity.")
public class Flight {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = "The database generated flight ID")
    private Long flightId;

    @Schema(description = "Flight number")
    @Column
    private int flightNumber;

    @Schema(description = "Time of departure")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy HH:mm:ss")
    @Column
    private LocalDateTime departureTime;

    @Schema(description = "Time of arrival")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy HH:mm:ss")
    @Column
    private LocalDateTime arrivalTime;

    @Schema(description = "Price of the flight")
    @Column
    private BigDecimal flightPrice;

    @Schema(description = "Number of rows in the airplane")
    @Column
    private int numRows;

    @Schema(description = "Type of airplane used for the flight")
    @Column
    private EFlightAirplane flightAirplane;

    @Schema(description = "Capacity of the airplane")
    @Column
    private int capacityPlane;

    @Schema(description = "Availability status of the flight")
    @Column
    private boolean availability;

    @Schema(description = "Duration of the flight")
    @Column
    private Duration flightDuration; // Nuevo campo para la duración del vuelo

    // Relación ManyToOne para el aeropuerto de origen
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "origin_airport_id")
    private Airport originAirport;

    // Relación ManyToOne para el aeropuerto de destino
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "destination_airport_id")
    private Airport destinationAirport;

    public Flight() {
    }

    public Flight(Long flightId, int flightNumber, LocalDateTime departureTime, LocalDateTime arrivalTime,
                  EFlightAirplane flightAirplane, int capacityPlane, boolean availability, int numRows,
                  BigDecimal flightPrice, Duration flightDuration, Airport originAirport, Airport destinationAirport) {
        this.flightId = flightId;
        this.flightNumber = flightNumber;
        this.departureTime = departureTime;
        this.arrivalTime = arrivalTime;
        this.flightAirplane = flightAirplane;
        this.capacityPlane = capacityPlane;
        this.availability = availability;
        this.numRows = numRows;
        this.flightPrice = flightPrice;
        this.flightDuration = flightDuration; // Inicialización de la duración
        this.originAirport = originAirport;
        this.destinationAirport = destinationAirport;
    }

    // Getters y setters...

    public Long getFlightId() {
        return flightId;
    }

    public void setFlightId(Long flightId) {
        this.flightId = flightId;
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

    public EFlightAirplane getFlightAirplane() {
        return flightAirplane;
    }

    public void setFlightAirplane(EFlightAirplane flightAirplane) {
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

    public int getNumRows() {
        return numRows;
    }

    public void setNumRows(int numRows) {
        this.numRows = numRows;
    }

    public BigDecimal getFlightPrice() {
        return flightPrice;
    }

    public void setFlightPrice(BigDecimal flightPrice) {
        this.flightPrice = flightPrice;
    }

    public Airport getOriginAirport() {
        return originAirport;
    }

    public void setOriginAirport(Airport originAirport) {
        this.originAirport = originAirport;
    }

    public Airport getDestinationAirport() {
        return destinationAirport;
    }

    public void setDestinationAirport(Airport destinationAirport) {
        this.destinationAirport = destinationAirport;
    }

    public Duration getFlightDuration() { // Método getter para flightDuration
        return flightDuration;
    }

    public void setFlightDuration(Duration flightDuration) { // Método setter para flightDuration
        this.flightDuration = flightDuration;
    }
}