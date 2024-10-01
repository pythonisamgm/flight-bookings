package com.flightbookings.flight_bookings.dtos.DTOSeat;


import com.flightbookings.flight_bookings.models.ESeatLetter;
import com.flightbookings.flight_bookings.models.Seat;

public class SeatConverter {

    public static SeatDTO toDTO(Seat seat) {
        if (seat == null) {
            return null;
        }
        SeatDTO dto = new SeatDTO();
        dto.setSeatId(seat.getSeatId());
        dto.setRow(seat.getRow());
        dto.setSeatLetter(seat.getSeatLetter().name());
        dto.setBooked(seat.isBooked());
        dto.setSeatName(seat.getSeatName());
        if (seat.getFlight() != null) {
            dto.setFlightId(seat.getFlight().getFlightId());
        }
        return dto;
    }

    public static Seat toEntity(SeatDTO dto) {
        if (dto == null) {
            return null;
        }
        Seat seat = new Seat();
        seat.setSeatId(dto.getSeatId());
        seat.setRow(dto.getRow());
        seat.setSeatLetter(ESeatLetter.valueOf(dto.getSeatLetter()));
        seat.setBooked(dto.isBooked());
        seat.setSeatName(dto.getSeatName());
        return seat;
    }
}
