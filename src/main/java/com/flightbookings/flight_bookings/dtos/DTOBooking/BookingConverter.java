package com.flightbookings.flight_bookings.dtos.DTOBooking;

import com.flightbookings.flight_bookings.models.Booking;
import com.flightbookings.flight_bookings.models.Flight;
import com.flightbookings.flight_bookings.services.interfaces.FlightService;
import jakarta.persistence.EntityNotFoundException;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Converter class for converting between Booking and BookingDTO objects.
 * This class uses ModelMapper for object mapping and integrates with FlightService
 * to resolve flight details when creating Booking objects from BookingDTOs.
 */
@Component
public class BookingConverter {

    private final ModelMapper modelMapper;
    private final FlightService flightService;

    /**
     * Constructs a BookingConverter with the specified ModelMapper and FlightService.
     *
     * @param modelMapper the ModelMapper used for object mapping
     * @param flightService the FlightService used to retrieve flight details
     */
    public BookingConverter(ModelMapper modelMapper, FlightService flightService) {
        this.modelMapper = modelMapper;
        this.flightService = flightService;
    }

    /**
     * Converts a BookingDTO to a Booking entity.
     *
     * @param bookingDTO the BookingDTO to convert
     * @return the converted Booking entity
     */
    public Booking dtoToBooking(BookingDTO bookingDTO) {
        Booking booking = modelMapper.map(bookingDTO, Booking.class);

        // Resuelve el Flight relacionado usando el FlightService
        if (bookingDTO.getFlightId() != null) {
            Flight flight = flightService.getFlightById(bookingDTO.getFlightId());
            if (flight != null) {
                booking.setFlight(flight);
            } else {
                throw new EntityNotFoundException("Flight with ID " + bookingDTO.getFlightId() + " not found");
            }
        }
        // Puedes agregar más lógica si Booking tiene más relaciones con otras entidades.

        return booking;
    }

    /**
     * Converts a Booking entity to a BookingDTO.
     *
     * @param booking the Booking entity to convert
     * @return the converted BookingDTO
     */
    public BookingDTO bookingToDto(Booking booking) {
        BookingDTO bookingDTO = modelMapper.map(booking, BookingDTO.class);

        // Maneja relaciones anidadas, como el ID de Flight
        if (booking.getFlight() != null) {
            bookingDTO.setFlightId(booking.getFlight().getFlightId());
        }
        // Agrega lógica para otras relaciones anidadas si las hay

        return bookingDTO;
    }

    /**
     * Converts a list of Booking entities to a list of BookingDTOs.
     *
     * @param bookings the list of Booking entities to convert
     * @return the list of converted BookingDTOs
     */
    public List<BookingDTO> bookingsToDtoList(List<Booking> bookings) {
        return bookings.stream()
                .map(this::bookingToDto) // Utiliza el método personalizado para convertir cada Booking a BookingDTO
                .collect(Collectors.toList());
    }

    /**
     * Converts a list of BookingDTOs to a list of Booking entities.
     *
     * @param bookingDTOs the list of BookingDTOs to convert
     * @return the list of converted Booking entities
     */
    public List<Booking> dtoListToBookings(List<BookingDTO> bookingDTOs) {
        return bookingDTOs.stream()
                .map(this::dtoToBooking) // Usa el método personalizado para convertir cada BookingDTO a Booking
                .collect(Collectors.toList());
    }

}
