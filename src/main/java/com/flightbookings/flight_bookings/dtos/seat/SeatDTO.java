package com.flightbookings.flight_bookings.dtos.seat;

public class SeatDTO {
    private Long seatId;
    private int row;
    private String seatLetter;
    private boolean booked;
    private Long flightId;
    private Long bookingId;

    public SeatDTO() {
    }

    public SeatDTO(Long seatId, int row, String seatLetter, boolean booked, Long flightId, Long bookingId) {
        this.seatId = seatId;
        this.row = row;
        this.seatLetter = seatLetter;
        this.booked = booked;
        this.flightId = flightId;
        this.bookingId = bookingId;
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

    public Long getFlightId() {
        return flightId;
    }

    public void setFlightId(Long flightId) {
        this.flightId = flightId;
    }

    public Long getBookingId() {
        return bookingId;
    }

    public void setBookingId(Long bookingId) {
        this.bookingId = bookingId;
    }
}