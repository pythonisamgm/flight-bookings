package com.flightbookings.flight_bookings.dtos.DTOSeat;

import io.swagger.v3.oas.annotations.media.Schema;
/**
 * Data Transfer Object for Seat.
 */
@Schema(description = "Data Transfer Object for Seat.")
public class SeatDTO {
    /**
     * The unique identifier of the seat.
     */
    @Schema(description = "The database generated seat ID")
    private Long seatId;
    /**
     * The row number of the seat.
     */
    @Schema(description = "The row number of the seat")
    private int row;
    /**
     * The letter of the seat in the row.
     */
    @Schema(description = "The letter of the seat in the row")
    private String seatLetter;
    /**
     * The booking status of the seat.
     */
    @Schema(description = "Booking status of the seat")
    private boolean booked;
    /**
     * The unique identifier for the seat (row + letter).
     */
    @Schema(description = "The unique identifier for the seat (row + letter)")
    private String seatName;
    /**
     * The flight ID associated with this seat.
     */
    @Schema(description = "The flight associated with this seat")
    private Long flightId;

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

    public String getSeatLetter() {
        return seatLetter;
    }

    public void setSeatLetter(String seatLetter) {
        this.seatLetter = seatLetter;
    }

    public boolean isBooked() {
        return booked;
    }

    public void setBooked(boolean booked) {
        this.booked = booked;
    }

    public String getSeatName() {
        return seatName;
    }

    public void setSeatName(String seatName) {
        this.seatName = seatName;
    }

    public Long getFlightId() {
        return flightId;
    }

    public void setFlightId(Long flightId) {
        this.flightId = flightId;
    }
}
