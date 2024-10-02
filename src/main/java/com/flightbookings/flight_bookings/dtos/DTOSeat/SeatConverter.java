package com.flightbookings.flight_bookings.dtos.DTOSeat;

import com.flightbookings.flight_bookings.models.ESeatLetter;
import com.flightbookings.flight_bookings.models.Seat;
import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;

public class SeatConverter {

    private final ModelMapper modelMapper;

    public SeatConverter(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
        configure();
    }

    private void configure() {
        modelMapper.addMappings(new PropertyMap<Seat, SeatDTO>() {
            @Override
            protected void configure() {
                map().setSeatLetter(source.getSeatLetter().name());
                map().setFlightId(source.getFlight() != null ? source.getFlight().getFlightId() : null);
            }
        });

        modelMapper.addMappings(new PropertyMap<SeatDTO, Seat>() {
            @Override
            protected void configure() {
                using(context -> ESeatLetter.valueOf(context.getSource())).map(source.getSeatLetter(), destination.getSeatLetter());
            }
        });
    }

    public SeatDTO convertToDto(Seat seat) {
        return modelMapper.map(seat, SeatDTO.class);
    }

    public Seat convertToEntity(SeatDTO seatDTO) {
        return modelMapper.map(seatDTO, Seat.class);
    }
}
