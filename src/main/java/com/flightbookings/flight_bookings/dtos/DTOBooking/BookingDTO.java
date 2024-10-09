package com.flightbookings.flight_bookings.dtos.DTOBooking;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import java.time.LocalDateTime;

/**
 * Data Transfer Object for Booking, representing the details of a booking in the system.
 */
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

    @Schema(description = "The ID of the user for this booking")
    @NotNull(message = "User ID cannot be null")
    @Positive(message = "User ID must be a positive number")
    private Long userId;

    // Default constructor
    public BookingDTO() {}

    /**
     * Constructor to create BookingDTO with all required fields.
     *
     * @param bookingId   the booking ID
     * @param flightId    the flight ID associated with the booking
     * @param passengerId the passenger ID associated with the booking
     * @param seatName    the name of the seat booked
     * @param userId      the user ID associated with the booking
     */
    public BookingDTO(Long bookingId, Long flightId, Long passengerId, String seatName, Long userId) {
        this.bookingId = bookingId;
        this.flightId = flightId;
        this.passengerId = passengerId;
        this.seatName = seatName;
        this.userId = userId;
    }

    /**
     * Gets the booking ID.
     *
     * @return the booking ID
     */
    public Long getBookingId() {
        return bookingId;
    }

    /**
     * Sets the booking ID.
     *
     * @param bookingId the booking ID to set
     */
    public void setBookingId(Long bookingId) {
        this.bookingId = bookingId;
    }

    /**
     * Gets the flight ID associated with this booking.
     *
     * @return the flight ID
     */
    public Long getFlightId() {
        return flightId;
    }

    /**
     * Sets the flight ID associated with this booking.
     *
     * @param flightId the flight ID to set
     */
    public void setFlightId(Long flightId) {
        this.flightId = flightId;
    }

    /**
     * Gets the passenger ID associated with this booking.
     *
     * @return the passenger ID
     */
    public Long getPassengerId() {
        return passengerId;
    }

    /**
     * Sets the passenger ID associated with this booking.
     *
     * @param passengerId the passenger ID to set
     */
    public void setPassengerId(Long passengerId) {
        this.passengerId = passengerId;
    }

    /**
     * Gets the name of the seat booked.
     *
     * @return the seat name
     */
    public String getSeatName() {
        return seatName;
    }

    /**
     * Sets the name of the seat booked.
     *
     * @param seatName the seat name to set
     */
    public void setSeatName(String seatName) {
        this.seatName = seatName;
    }

    /**
     * Gets the user ID associated with this booking.
     *
     * @return the user ID
     */
    public Long getUserId() {
        return userId;
    }

    /**
     * Sets the user ID associated with this booking.
     *
     * @param userId the user ID to set
     */
    public void setUserId(Long userId) {
        this.userId = userId;
    }
}
