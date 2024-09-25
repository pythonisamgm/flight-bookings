package com.flightbookings.flight_bookings.models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;

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

    @Column
    private LocalDateTime departureTime;

    @Column
    private LocalDateTime arrivalTime;

    @Column
    private int capacityPlane;

    @OneToMany(mappedBy = "flight")
    @JsonManagedReference
    private List<Seat> seatList;

    @OneToMany(mappedBy = "flight")
    @JsonManagedReference
    private List<Booking> bookingList;

    @ManyToMany(mappedBy = "flight", fetch = FetchType.LAZY)
    @JsonBackReference
    private Set<Airport> airports;

}