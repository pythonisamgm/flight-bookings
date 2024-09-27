package com.flightbookings.flight_bookings.models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;

import java.time.LocalDateTime;
@Entity
@Table(name= "booking")
public class Booking {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long bookingId;

    @Column
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy HH:mm:ss")
    private LocalDateTime dateOfBooking = LocalDateTime.now();

    @OneToOne(mappedBy = "booking")
    @JsonManagedReference(value = "booking-passenger")
    private Passenger passenger;

    @ManyToOne
    @JoinColumn(name = "flight_id", nullable = false)
    @JsonBackReference(value="booking-flight")
    private Flight flight;

    @OneToOne
    @JoinColumn(name = "seat_id")
    @JsonBackReference(value="booking-seat")
    private Seat seat;

    @ManyToOne
    @JoinColumn(name = "user_id")
    @JsonBackReference(value="user-booking")
    private User user;

    // Getters, Setters, Constructors



    public Booking() {
    }

    public Booking(Long bookingId, LocalDateTime dateOfBooking, Passenger passenger, Flight flight, Seat seat) {
        this.bookingId = bookingId;
        this.dateOfBooking = dateOfBooking;
        this.passenger = passenger;
        this.flight = flight;
        this.seat = seat;
    }

    public Long getBookingId() {
        return bookingId;
    }

    public void setBookingId(Long bookingId) {
        this.bookingId = bookingId;
    }

    public LocalDateTime getDateOfBooking() {
        return dateOfBooking;
    }

    public void setDateOfBooking(LocalDateTime dateOfBooking) {
        this.dateOfBooking = dateOfBooking;
    }

    public Passenger getPassenger() {
        return passenger;
    }

    public void setPassenger(Passenger passenger) {
        this.passenger = passenger;
    }

    public Flight getFlight() {
        return flight;
    }

    public void setFlight(Flight flight) {
        this.flight = flight;
    }
    public Seat getSeat() {
        return seat;
    }

    public void setSeat(Seat seat) {
        this.seat = seat;
    }
}
