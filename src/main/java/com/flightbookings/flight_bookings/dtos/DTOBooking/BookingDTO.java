package com.flightbookings.flight_bookings.dtos.DTOBooking;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;

import java.time.LocalDateTime;
/**
 * Data Transfer Object for Booking.
 */
@Schema(description = "Data Transfer Object for Booking")
public class BookingDTO {
    /**
     * The unique identifier of the booking.
     */
    @Schema(description = "The database generated booking ID")
    private Long bookingId;
    /**
     * The date and time when the booking was made.
     */
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy HH:mm:ss")
    @Schema(description = "The date and time when the booking was made")
    private LocalDateTime dateOfBooking;
    /**
     * The ID of the passenger associated with the booking.
     */
    @Schema(description = "The passenger associated with this booking")
    private Long passengerId;
    /**
     * The ID of the flight associated with the booking.
     */
    @Schema(description = "The flight associated with this booking")
    private Long flightId;
    /**
     * The ID of the seat reserved in the booking.
     */
    @Schema(description = "The seat reserved in this booking")
    private Long seatId;
    /**
     * The ID of the user who made the booking.
     */
    @Schema(description = "The user who made the booking")
    private Long userId;

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

    public Long getPassengerId() {
        return passengerId;
    }

    public void setPassengerId(Long passengerId) {
        this.passengerId = passengerId;
    }

    public Long getFlightId() {
        return flightId;
    }

    public void setFlightId(Long flightId) {
        this.flightId = flightId;
    }

    public Long getSeatId() {
        return seatId;
    }

    public void setSeatId(Long seatId) {
        this.seatId = seatId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }
}
