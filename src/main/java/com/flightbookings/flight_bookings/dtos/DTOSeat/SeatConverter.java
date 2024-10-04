package com.flightbookings.flight_bookings.dtos.DTOSeat;

import com.flightbookings.flight_bookings.models.Seat;
import com.flightbookings.flight_bookings.services.interfaces.SeatService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class SeatConverter {

    private final ModelMapper modelMapper;
    private final SeatService seatService;

    public SeatConverter(ModelMapper modelMapper, SeatService seatService) {
        this.modelMapper = modelMapper;
        this.seatService = seatService;
    }

    public Seat dtoToSeat(SeatDTO seatDTO) {
        return modelMapper.map(seatDTO, Seat.class);
    }

    public SeatDTO seatToDto(Seat seat) {
        return modelMapper.map(seat, SeatDTO.class);
    }

    public List<SeatDTO> seatsToDtoList(List<Seat> seats) {
        return seats.stream()
                .map(this::seatToDto)
                .collect(Collectors.toList());
    }

    public List<Seat> dtoListToSeats(List<SeatDTO> seatDTOs) {
        return seatDTOs.stream()
                .map(this::dtoToSeat)
                .collect(Collectors.toList());
    }
}
