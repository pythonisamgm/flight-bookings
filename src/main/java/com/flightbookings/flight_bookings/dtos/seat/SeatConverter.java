package com.flightbookings.flight_bookings.dtos.seat;

import com.flightbookings.flight_bookings.exceptions.SeatNotFoundException;
import com.flightbookings.flight_bookings.models.Flight;
import com.flightbookings.flight_bookings.models.Seat;
import com.flightbookings.flight_bookings.models.ESeatLetter;
import com.flightbookings.flight_bookings.services.FlightServiceImpl;
import com.flightbookings.flight_bookings.services.SeatServiceImpl;
import org.springframework.stereotype.Component;

@Component
public class SeatConverter {

    private final FlightServiceImpl flightService;
    private final SeatServiceImpl seatService;

    public SeatConverter(FlightServiceImpl flightService, SeatServiceImpl seatService) {
        this.flightService = flightService;
        this.seatService = seatService;
    }

    public SeatDTO seatToDto(Seat seat) {
        SeatDTO seatDTO = new SeatDTO();
        seatDTO.setSeatId(seat.getSeatId());
        seatDTO.setRow(seat.getRow());
        seatDTO.setSeatLetter(seat.getSeatLetter().name());
        seatDTO.setBooked(seat.isBooked());
        seatDTO.setFlightId(seat.getFlight() != null ? seat.getFlight().getFlightId() : null);
        seatDTO.setBookingId(seat.getBooking() != null ? seat.getBooking().getBookingId() : null);
        return seatDTO;
    }

    public Seat dtoToSeat(SeatDTO seatDTO) {
        Seat seat = new Seat();
        seat.setSeatId(seatDTO.getSeatId());
        seat.setRow(seatDTO.getRow());
        seat.setSeatLetter(ESeatLetter.valueOf(seatDTO.getSeatLetter()));
        seat.setBooked(seatDTO.isBooked());

        if (seatDTO.getFlightId() != null) {
            Flight flight = flightService.getFlightById(seatDTO.getFlightId());
            if (flight == null) {
                throw new SeatNotFoundException("Flight not found with ID: " + seatDTO.getFlightId());
            }
            seat.setFlight(flight);
        }

        return seat;
    }
}
