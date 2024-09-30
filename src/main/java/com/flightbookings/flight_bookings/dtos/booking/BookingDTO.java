package com.flightbookings.flight_bookings.dtos.booking;

import java.time.LocalDateTime;

public class BookingDTO {
    private Long bookingId;
    private LocalDateTime dateOfBooking;
    private Long passengerId;
    private Long flightId;
    private Long seatId;
    private Long userId;

    public BookingDTO() {
    }

    public BookingDTO(Long bookingId, LocalDateTime dateOfBooking, Long passengerId, Long flightId, Long seatId, Long userId) {
        this.bookingId = bookingId;
        this.dateOfBooking = dateOfBooking;
        this.passengerId = passengerId;
        this.flightId = flightId;
        this.seatId = seatId;
        this.userId = userId;
    }

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

