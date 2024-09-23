package com.flightbookings.flight_bookings.models;

import jakarta.persistence.*;

public class Seat {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long seatId;

    private int row;

    @Enumerated(EnumType.STRING)
    private ESeatLetter seatLetter;
}
