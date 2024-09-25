package com.flightbookings.flight_bookings.models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "Task")
public class Flight {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column
    private int flightNumber;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy HH:mm:ss")
    @Column
    private LocalDateTime departureTime;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy HH:mm:ss")
    @Column
    private LocalDateTime arrivalTime;

    @Column
    private EFlightAirplane flightAirplane;

    @Column
    private int capacityPlane;

    @Column
    private boolean availability;

    @Column
    private int numRows;

    @Column
    private BigDecimal flightPrice;

    @OneToMany(mappedBy = "flight")
    @JsonManagedReference
    private List<Seat> seatList;

    @OneToMany(mappedBy = "flight")
    @JsonManagedReference
    private List<Booking> bookingList;

    @ManyToMany(mappedBy = "flight", fetch = FetchType.LAZY)
    @JsonBackReference
    private Set<Airport> airports;

    public Flight(Long id, int flightNumber, LocalDateTime departureTime, LocalDateTime arrivalTime,
                  EFlightAirplane flightAirplane, int capacityPlane, boolean availability, int numRows,
                  BigDecimal flightPrice, List<Seat> seatList, List<Booking> bookingList, Set<Airport> airports) {
        this.id = id;
        this.flightNumber = flightNumber;
        this.departureTime = departureTime;
        this.arrivalTime = arrivalTime;
        this.flightAirplane = flightAirplane;
        this.capacityPlane = capacityPlane;
        this.availability = availability;
        this.numRows = numRows;
        this.flightPrice = flightPrice;
        this.seatList = seatList;
        this.bookingList = bookingList;
        this.airports = airports;
    }

    public Flight() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public List<Seat> getSeatList() {
        return seatList;
    }

    public void setSeatList(List<Seat> seatList) {
        this.seatList = seatList;
    }

    public List<Booking> getBookingList() {
        return bookingList;
    }

    public void setBookingList(List<Booking> bookingList) {
        this.bookingList = bookingList;
    }

    public Set<Airport> getAirports() {
        return airports;
    }

    public void setAirports(Set<Airport> airports) {
        this.airports = airports;
    }
}