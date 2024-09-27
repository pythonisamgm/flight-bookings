package com.flightbookings.flight_bookings.models;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import jakarta.persistence.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "flight")
@ApiModel(description = "All details about the Flight entity.")
public class Flight {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @ApiModelProperty(notes = "The database generated flight ID")
    private Long flightId;

    @ApiModelProperty(notes = "Number of rows in the airplane")
    @Column
    private int numRows;

    @ApiModelProperty(notes = "Flight number")
    @Column
    private int flightNumber;

    @ApiModelProperty(notes = "Time of departure")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy HH:mm:ss")
    @Column
    private LocalDateTime departureTime;

    @ApiModelProperty(notes = "Time of arrival")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy HH:mm:ss")
    @Column
    private LocalDateTime arrivalTime;

    @ApiModelProperty(notes = "Type of airplane used for the flight")
    @Column
    private EFlightAirplane flightAirplane;

    @ApiModelProperty(notes = "Capacity of the airplane")
    @Column
    private int capacityPlane;

    @ApiModelProperty(notes = "Availability status of the flight")
    @Column
    private boolean availability;

    @ApiModelProperty(notes = "Price of the flight")
    @Column
    private BigDecimal flightPrice;

    @OneToMany(mappedBy = "flight")
    @JsonManagedReference(value = "flight-seat")
    @ApiModelProperty(notes = "List of seats associated with this flight.")
    private List<Seat> seats = new ArrayList<>();

    @OneToMany(mappedBy = "flight")
    @JsonManagedReference(value = "booking-flight")
    @ApiModelProperty(notes = "List of bookings associated with this flight.")
    private List<Booking> bookingList;

    /*@ManyToMany(mappedBy = "flight", fetch = FetchType.LAZY)
    @JsonBackReference
    @ApiModelProperty(notes = "List of airports associated with this flight.")
    private Set<Airport> airports;*/


    public Flight() {
    }

//    public Flight(Long id, int flightNumber, LocalDateTime departureTime, LocalDateTime arrivalTime,
//                  EFlightAirplane flightAirplane, int capacityPlane, boolean availability, int numRows,
//                  BigDecimal flightPrice, List<Seat> seatList, List<Booking> bookingList, Set<Airport> airports) {
//        this.flightId = id;
//        this.flightNumber = flightNumber;
//        this.departureTime = departureTime;
//        this.arrivalTime = arrivalTime;
//        this.flightAirplane = flightAirplane;
//        this.capacityPlane = capacityPlane;
//        this.availability = availability;
//        this.numRows = numRows;
//        this.flightPrice = flightPrice;
//        this.seats = seats;
//        this.bookingList = bookingList;
//        this.airports = airports;
//    }


    public Flight(Long flightId, int numRows, int flightNumber, LocalDateTime departureTime, LocalDateTime arrivalTime, EFlightAirplane flightAirplane, int capacityPlane, boolean availability, BigDecimal flightPrice, List<Seat> seats, List<Booking> bookingList) {
        this.flightId = flightId;
        this.numRows = numRows;
        this.flightNumber = flightNumber;
        this.departureTime = departureTime;
        this.arrivalTime = arrivalTime;
        this.flightAirplane = flightAirplane;
        this.capacityPlane = capacityPlane;
        this.availability = availability;
        this.flightPrice = flightPrice;
        this.seats = seats;
        this.bookingList = bookingList;
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

    public List<Seat> getSeats() {
        return seats;
    }

    public void setSeats(List<Seat> seats) {
        this.seats = seats;
    }

    public List<Booking> getBookingList() {
        return bookingList;
    }

    public void setBookingList(List<Booking> bookingList) {
        this.bookingList = bookingList;
    }

//    public Set<Airport> getAirports() {
//        return airports;
//    }
//
//    public void setAirports(Set<Airport> airports) {
//        this.airports = airports;
//    }
}