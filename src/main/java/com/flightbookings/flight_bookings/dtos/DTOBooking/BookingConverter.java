package com.flightbookings.flight_bookings.dtos.DTOBooking;

import com.flightbookings.flight_bookings.exceptions.FlightNotFoundException;
import com.flightbookings.flight_bookings.exceptions.PassengerNotFoundException;
import com.flightbookings.flight_bookings.exceptions.SeatNotFoundException;
import com.flightbookings.flight_bookings.exceptions.UserNotFoundException;
import com.flightbookings.flight_bookings.models.*;
import com.flightbookings.flight_bookings.services.interfaces.FlightService;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Converter class for converting between Booking and BookingDTO objects.
 * This class uses manual mapping to convert fields between objects without using ModelMapper.
 */
@Component
public class BookingConverter {

    private final FlightService flightService;

    /**
     * Constructs a BookingConverter with the specified FlightService.
     *
     * @param flightService the FlightService used to retrieve flight details
     */
    public BookingConverter(FlightService flightService) {
        this.flightService = flightService;
    }

    /**
     * Converts a BookingDTO to a Booking entity.
     *
     * @param bookingDTO the BookingDTO to convert
     * @return the converted Booking entity
     * @throws FlightNotFoundException if Flight ID is missing in bookingDTO
     * @throws PassengerNotFoundException if Passenger ID is missing in bookingDTO
     * @throws SeatNotFoundException if Seat Name is missing in bookingDTO
     * @throws UserNotFoundException if User ID is missing in bookingDTO
     */
    public Booking dtoToBooking(BookingDTO bookingDTO) {
        Booking booking = new Booking();

        if (bookingDTO.getFlightId() != null) {
            Flight flight = new Flight();
            flight.setFlightId(bookingDTO.getFlightId());
            booking.setFlight(flight);
        } else {
            throw new FlightNotFoundException("Flight ID is missing for booking DTO");
        }

        if (bookingDTO.getPassengerId() != null) {
            Passenger passenger = new Passenger();
            passenger.setPassengerId(bookingDTO.getPassengerId());
            booking.setPassenger(passenger);
        } else {
            throw new PassengerNotFoundException("Passenger ID is missing for booking DTO");
        }

        if (bookingDTO.getSeatName() != null) {
            Seat seat = new Seat();
            seat.setSeatName(bookingDTO.getSeatName());
            booking.setSeat(seat);
        } else {
            throw new SeatNotFoundException("Seat name is missing for booking DTO");
        }

        if (bookingDTO.getUserId() != null) {
            User user = new User();
            user.setUserId(bookingDTO.getUserId());
            booking.setUser(user);
        } else {
            throw new UserNotFoundException("User ID is missing for booking DTO");
        }

        booking.setBookingId(bookingDTO.getBookingId());
        return booking;
    }

    /**
     * Converts a Booking entity to a BookingDTO.
     *
     * @param booking the Booking entity to convert
     * @return the converted BookingDTO
     */
    public BookingDTO bookingToDto(Booking booking) {
        BookingDTO bookingDTO = new BookingDTO();

        if (booking.getFlight() != null) {
            bookingDTO.setFlightId(booking.getFlight().getFlightId());
        } else {
            throw new FlightNotFoundException("Flight not found for booking ID: " + booking.getBookingId());
        }

        if (booking.getPassenger() != null) {
            bookingDTO.setPassengerId(booking.getPassenger().getPassengerId());
        } else {
            throw new PassengerNotFoundException("Passenger not found for booking ID: " + booking.getBookingId());
        }

        if (booking.getSeat() != null) {
            bookingDTO.setSeatName(booking.getSeat().getSeatName());
        } else {
            throw new SeatNotFoundException("Seat not assigned for booking ID: " + booking.getBookingId());
        }

        if (booking.getUser() != null) {
            bookingDTO.setUserId(booking.getUser().getUserId());
        } else {
            throw new UserNotFoundException("User not found for booking ID: " + booking.getBookingId());
        }

        bookingDTO.setBookingId(booking.getBookingId());
        return bookingDTO;
    }

    /**
     * Converts a list of Booking entities to a list of BookingDTO objects.
     *
     * @param bookings The list of Booking entities to convert.
     * @return A list of corresponding BookingDTO objects.
     */
    public List<BookingDTO> bookingsToDtoList(List<Booking> bookings) {
        return bookings.stream()
                .map(this::bookingToDto)
                .collect(Collectors.toList());
    }

    /**
     * Converts a list of BookingDTO objects to a list of Booking entities.
     *
     * @param bookingDTOs The list of BookingDTOs to convert.
     * @return A list of corresponding Booking entities.
     */
    public List<Booking> dtoListToBookings(List<BookingDTO> bookingDTOs) {
        return bookingDTOs.stream()
                .map(this::dtoToBooking)
                .collect(Collectors.toList());
    }
}
