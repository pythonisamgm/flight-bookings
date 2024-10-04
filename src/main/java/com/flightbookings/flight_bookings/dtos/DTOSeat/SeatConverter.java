package com.flightbookings.flight_bookings.dtos.DTOSeat;

import com.flightbookings.flight_bookings.models.Seat;
import com.flightbookings.flight_bookings.services.interfaces.BookingService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class SeatConverter {

    private final ModelMapper modelMapper;
    private final BookingService bookingService;

    public SeatConverter(ModelMapper modelMapper, BookingService bookingService) {
        this.modelMapper = modelMapper;
        this.bookingService = bookingService;
    }

    public Seat dtoToSeat(SeatDTO seatDTO) {
        return modelMapper.map(seatDTO, Seat.class);
    }

    public SeatDTO seatToDto(Seat seat) {
        return modelMapper.map(seat, SeatDTO.class);
    }

    public List<SeatDTO> seatsToDtoList(List<Seat> seats) {
        List<SeatDTO> seatDTOs = new ArrayList<>();
        for (Seat seat : seats) {
            seatDTOs.add(seatToDto(seat));
        }
        return seatDTOs;
    }

    public List<Seat> dtoListToSeats(List<SeatDTO> seatDTOs) {
        List<Seat> seats = new ArrayList<>();
        for (SeatDTO seatDTO : seatDTOs) {
            seats.add(dtoToSeat(seatDTO));
        }
        return seats;
    }
}
