package com.flightbookings.flight_bookings.dtos.DTOSeat;

import com.flightbookings.flight_bookings.models.Seat;
import com.flightbookings.flight_bookings.services.interfaces.SeatService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Converter class for converting between Seat and SeatDTO objects.
 * This class utilizes ModelMapper for object mapping.
 */
@Component
public class SeatConverter {

    private final ModelMapper modelMapper;
    private final SeatService seatService;

    /**
     * Constructs a SeatConverter with the specified ModelMapper and SeatService.
     *
     * @param modelMapper the ModelMapper used for object mapping
     * @param seatService the SeatService used for seat-related operations
     */
    public SeatConverter(ModelMapper modelMapper, SeatService seatService) {
        this.modelMapper = modelMapper;
        this.seatService = seatService;
    }

    /**
     * Converts a SeatDTO to a Seat entity.
     *
     * @param seatDTO the SeatDTO to convert
     * @return the converted Seat entity
     */
    public Seat dtoToSeat(SeatDTO seatDTO) {
        return modelMapper.map(seatDTO, Seat.class);
    }

    /**
     * Converts a Seat entity to a SeatDTO.
     *
     * @param seat the Seat entity to convert
     * @return the converted SeatDTO
     */
    public SeatDTO seatToDto(Seat seat) {
        return modelMapper.map(seat, SeatDTO.class);
    }

    /**
     * Converts a list of Seat entities to a list of SeatDTOs.
     *
     * @param seats the list of Seat entities to convert
     * @return the list of converted SeatDTOs
     */
    public List<SeatDTO> seatsToDtoList(List<Seat> seats) {
        return seats.stream()
                .map(this::seatToDto)
                .collect(Collectors.toList());
    }

    /**
     * Converts a list of SeatDTOs to a list of Seat entities.
     *
     * @param seatDTOs the list of SeatDTOs to convert
     * @return the list of converted Seat entities
     */
    public List<Seat> dtoListToSeats(List<SeatDTO> seatDTOs) {
        return seatDTOs.stream()
                .map(this::dtoToSeat)
                .collect(Collectors.toList());
    }
}
