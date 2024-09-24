package com.flightbookings.flight_bookings.models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name= "Booking")
public class Booking {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long bookingId;

    @Column
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy HH:mm")
    private LocalDateTime dateOfBooking = LocalDateTime.now();

//    @Column
//    private boolean status;

    @OneToOne(mappedBy = "booking")
    @JsonManagedReference("booking-passenger")
    private Passenger passenger;

    @ManyToOne
    @JoinColumn(name = "flight_id", nullable = false)
    @JsonBackReference("flight-booking")
    private Flight flight;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    @JsonBackReference("user-booking")
    private User user;

    public Booking() {
    }

    public Booking(Flight flight, Passenger passenger, LocalDateTime dateOfBooking, Long bookingId) {
        this.flight = flight;
        this.passenger = passenger;
        this.dateOfBooking = dateOfBooking;
        this.bookingId = bookingId;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
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
}
