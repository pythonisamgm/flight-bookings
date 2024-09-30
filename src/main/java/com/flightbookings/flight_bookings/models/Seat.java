package com.flightbookings.flight_bookings.models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;

@Entity
@Table(name="seat", uniqueConstraints = {
        @UniqueConstraint(columnNames = {"seat_name", "flight_id"})
})
@Schema(description = "All details about the Seat entity.")
public class Seat {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    @Schema(description = "The database generated seat ID")
    private Long seatId;

    @Column
    @Schema(description = "The row number of the seat")
    private int row;

    @Enumerated(EnumType.STRING)
    @Column
    @Schema(description = "The letter of the seat in the row")
    private ESeatLetter seatLetter;

    @Column
    @Schema(description = "Booking status of the seat")
    private boolean booked = false;

    @Column(unique = true, nullable = false)
    @Schema(description = "The unique identifier for the seat (row + letter)")
    private String seatName;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name="flight_id", nullable = false)
    @JsonBackReference(value = "flight-seat")
    @Schema(description = "The flight associated with this seat")
    private Flight flight;

    @OneToOne(mappedBy = "seat", cascade = CascadeType.ALL)
    @JsonManagedReference(value = "booking-seat")
    @Schema(description = "The booking associated with this seat")
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
