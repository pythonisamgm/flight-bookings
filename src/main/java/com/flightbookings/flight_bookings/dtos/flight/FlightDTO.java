package com.flightbookings.flight_bookings.dtos.flight;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class FlightDTO {
    private Long flightId;
    private int flightNumber;
    private LocalDateTime departureTime;
    private LocalDateTime arrivalTime;
    private String flightAirplane;
    private int capacityPlane;
    private boolean availability;
    private BigDecimal flightPrice;

    public FlightDTO() {
    }

    public FlightDTO(Long flightId, int flightNumber, LocalDateTime departureTime, LocalDateTime arrivalTime, String flightAirplane, int capacityPlane, boolean availability, BigDecimal flightPrice) {
        this.flightId = flightId;
        this.flightNumber = flightNumber;
        this.departureTime = departureTime;
        this.arrivalTime = arrivalTime;
        this.flightAirplane = flightAirplane;
        this.capacityPlane = capacityPlane;
        this.availability = availability;
        this.flightPrice = flightPrice;
    }

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
}
