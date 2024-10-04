package com.flightbookings.flight_bookings.dtos.DTOBooking;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

@Schema(description = "Data Transfer Object for Booking.")
public class BookingDTO {

    @Schema(description = "The database generated booking ID")
    @Positive(message = "Booking ID must be a positive number")
    private Long bookingId;

    @Schema(description = "The ID of the flight associated with this booking")
    @NotNull(message = "Flight ID cannot be null")
    @Positive(message = "Flight ID must be a positive number")
    private Long flightId;

    @Schema(description = "The ID of the passenger for this booking")
    @NotNull(message = "Passenger ID cannot be null")
    @Positive(message = "Passenger ID must be a positive number")
    private Long passengerId;

    @Schema(description = "The name of the seat booked")
    @NotNull(message = "Seat name cannot be null")
    private String seatName;


    public BookingDTO() {}

    public Long getBookingId() {
        return bookingId;
    }

    public void setBookingId(Long bookingId) {
        this.bookingId = bookingId;
    }

    public Long getFlightId() {
        return flightId;
    }

    public void setFlightId(Long flightId) {
        this.flightId = flightId;
    }

    public Long getPassengerId() {
        return passengerId;
    }

    public void setPassengerId(Long passengerId) {
        this.passengerId = passengerId;
    }

    public String getSeatName() {
        return seatName;
    }

    public void setSeatName(String seatName) {
        this.seatName = seatName;
    }
}
