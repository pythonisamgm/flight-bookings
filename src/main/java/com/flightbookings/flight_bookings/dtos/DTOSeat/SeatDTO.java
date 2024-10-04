package com.flightbookings.flight_bookings.dtos.DTOSeat;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

@Schema(description = "Data Transfer Object for Seat.")
public class SeatDTO {

    @Schema(description = "The database generated seat ID")
    private Long seatId;

    @Schema(description = "The row number of the seat")
    @NotNull(message = "Row number cannot be null")
    @Min(value = 1, message = "Row number must be at least 1")
    private int row;

    @Schema(description = "The letter of the seat in the row")
    @NotNull(message = "Seat letter cannot be null")
    @Pattern(regexp = "[A-Z]", message = "Seat letter must be a single uppercase letter")
    private String seatLetter;

    @Schema(description = "Booking status of the seat")
    private boolean booked;

    @Schema(description = "The unique identifier for the seat (row + letter)")
    private String seatName;

    @Schema(description = "The flight associated with this seat")
    @NotNull(message = "Flight ID cannot be null")
    private Long flightId;

    public SeatDTO() {}

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
