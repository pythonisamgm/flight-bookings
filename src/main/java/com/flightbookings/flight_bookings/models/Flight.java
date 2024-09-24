package com.flightbookings.flight_bookings.models;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "Task")

public class Flight {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    public Flight() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Flight(Long id) {
        this.id = id;
    }

    //@OneToMany(mappedBy = "flight")
    //@JsonManagedReference("flight-seat")
    //private List<Seat> seatList;

    @OneToMany(mappedBy = "flight")
    @JsonManagedReference("flight-booking")
    private List<Booking> bookings;

}