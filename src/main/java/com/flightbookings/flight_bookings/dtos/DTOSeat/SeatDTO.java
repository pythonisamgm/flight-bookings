package com.flightbookings.flight_bookings.dtos.DTOSeat;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

/**
 * Data Transfer Object for Seat.
 * This class represents the data that will be transferred between the client and server
 * related to the Seat entity.
 */
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

    /**
     * Default constructor for SeatDTO.
     */
    public SeatDTO() {}

    /**
     * Gets the seat ID.
     *
     * @return the seat ID
     */
    public Long getSeatId() {
        return seatId;
    }

    /**
     * Sets the seat ID.
     *
     * @param seatId the seat ID to set
     */
    public void setSeatId(Long seatId) {
        this.seatId = seatId;
    }

    /**
     * Gets the row number of the seat.
     *
     * @return the row number
     */
    public int getRow() {
        return row;
    }

    /**
     * Sets the row number of the seat.
     *
     * @param row the row number to set
     */
    public void setRow(int row) {
        this.row = row;
    }

    /**
     * Gets the letter of the seat in the row.
     *
     * @return the seat letter
     */
    public String getSeatLetter() {
        return seatLetter;
    }

    /**
     * Sets the letter of the seat in the row.
     *
     * @param seatLetter the seat letter to set
     */
    public void setSeatLetter(String seatLetter) {
        this.seatLetter = seatLetter;
    }

    /**
     * Checks if the seat is booked.
     *
     * @return true if the seat is booked, false otherwise
     */
    public boolean isBooked() {
        return booked;
    }

    /**
     * Sets the booking status of the seat.
     *
     * @param booked the booking status to set
     */
    public void setBooked(boolean booked) {
        this.booked = booked;
    }

    /**
     * Gets the unique identifier for the seat (row + letter).
     *
     * @return the seat name
     */
    public String getSeatName() {
        return seatName;
    }

    /**
     * Sets the unique identifier for the seat (row + letter).
     *
     * @param seatName the seat name to set
     */
    public void setSeatName(String seatName) {
        this.seatName = seatName;
    }

    /**
     * Gets the flight ID associated with this seat.
     *
     * @return the flight ID
     */
    public Long getFlightId() {
        return flightId;
    }

    /**
     * Sets the flight ID associated with this seat.
     *
     * @param flightId the flight ID to set
     */
    public void setFlightId(Long flightId) {
        this.flightId = flightId;
    }
}
