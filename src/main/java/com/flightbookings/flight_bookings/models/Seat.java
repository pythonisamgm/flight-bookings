package com.flightbookings.flight_bookings.models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import jakarta.persistence.*;

@Entity
@Table(name="seat")
@ApiModel(description = "All details about the Seat entity.")
public class Seat {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    @ApiModelProperty(notes = "The database generated seat ID")
    private Long seatId;

    @Column
    @ApiModelProperty(notes = "The row number of the seat")
    private int row;

    @Enumerated(EnumType.STRING)
    @Column
    @ApiModelProperty(notes = "The letter of the seat in the row")
    private ESeatLetter seatLetter;

    @Column
    @ApiModelProperty(notes = "Booking status of the seat")
    private boolean booked = false;

    @Column(unique = true, nullable = false)
    @ApiModelProperty(notes = "The unique identifier for the seat (row + letter)")
    private String seatName;

    @ManyToOne
    @JoinColumn(name="flight_id", nullable = false)
    @JsonBackReference(value = "flight-seat")
    @ApiModelProperty(notes = "The flight associated with this seat")
    private Flight flight;

    @OneToOne(mappedBy = "seat")
    @JsonManagedReference(value = "booking-seat")
    @ApiModelProperty(notes = "The booking associated with this seat")
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
