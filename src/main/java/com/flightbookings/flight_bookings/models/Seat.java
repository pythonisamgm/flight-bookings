package com.flightbookings.flight_bookings.models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;

@Entity
@Table(name="seat")
public class Seat {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private Long seatId;

    @Column
    private int row;

    @Enumerated(EnumType.STRING)
    @Column
    private ESeatLetter seatLetter;

    @Column
    private boolean booked = false;

    @Column(unique = true, nullable = false)
    private String seatName;

    @ManyToOne
    @JoinColumn(name="flight_id", nullable = false)
    @JsonBackReference(value = "flight-seat")
    private Flight flight;

    @OneToOne(mappedBy = "seat")
    @JsonManagedReference(value = "booking-seat")
    private Booking booking;

    public Seat() {
    }

    public Seat(Long seatId, int row, ESeatLetter seatLetter, boolean booked, Flight flight, Booking booking) {
        this.seatId = seatId;
        this.row = row;
        this.seatLetter = seatLetter;
        this.booked = booked;
        this.seatName = row + seatLetter.name();
        this.flight = flight;
        this.booking = booking;
    }

    public Long getSeatId() {
        return seatId;
    }

    public void setSeatId(Long seatId) {
        this.seatId = seatId;
    }

    public int getRow() {
        return row;
    }

    public void setRow(int row) {
        this.row = row;
    }

    public ESeatLetter getSeatLetter() {
        return seatLetter;
    }

    public void setSeatLetter(ESeatLetter seatLetter) {
        this.seatLetter = seatLetter;
    }

    public boolean isBooked() {
        return booked;
    }

    public void setBooked(boolean booked) {
        this.booked = booked;
    }

    public Flight getFlight() {
        return flight;
    }

    public void setFlight(Flight flight) {
        this.flight = flight;
    }

    public Booking getBooking() {
        return booking;
    }

    public void setBooking(Booking booking) {
        this.booking = booking;
    }

    public String getSeatName() {
        return seatName;
    }

    public void setSeatName(String seatName) {
        this.seatName = seatName;
    }
}
