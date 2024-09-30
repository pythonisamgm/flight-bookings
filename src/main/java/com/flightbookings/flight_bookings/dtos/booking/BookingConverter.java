package com.flightbookings.flight_bookings.dtos.booking;

import com.flightbookings.flight_bookings.exceptions.*;
import com.flightbookings.flight_bookings.models.Booking;
import com.flightbookings.flight_bookings.models.Flight;
import com.flightbookings.flight_bookings.models.Passenger;
import com.flightbookings.flight_bookings.models.Seat;
import com.flightbookings.flight_bookings.models.User;
import com.flightbookings.flight_bookings.services.FlightServiceImpl;
import com.flightbookings.flight_bookings.services.PassengerServiceImpl;
import com.flightbookings.flight_bookings.services.SeatServiceImpl;
import com.flightbookings.flight_bookings.services.UserServiceImpl;

import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class BookingConverter {

    private final FlightServiceImpl flightService;
    private final PassengerServiceImpl passengerService;
    private final SeatServiceImpl seatService;
    private final UserServiceImpl userService;

    public BookingConverter(FlightServiceImpl flightService, PassengerServiceImpl passengerService, SeatServiceImpl seatService, UserServiceImpl userService) {
        this.flightService = flightService;
        this.passengerService = passengerService;
        this.seatService = seatService;
        this.userService = userService;
    }

    public BookingDTO bookingToDto(Booking booking) {
        BookingDTO bookingDTO = new BookingDTO();
        bookingDTO.setBookingId(booking.getBookingId());
        bookingDTO.setDateOfBooking(booking.getDateOfBooking());
        bookingDTO.setPassengerId(booking.getPassenger() != null ? booking.getPassenger().getPassengerId() : null);
        bookingDTO.setFlightId(booking.getFlight() != null ? booking.getFlight().getFlightId() : null);
        bookingDTO.setSeatId(booking.getSeat() != null ? booking.getSeat().getSeatId() : null);
        bookingDTO.setUserId(booking.getUser() != null ? booking.getUser().getId() : null);
        return bookingDTO;
    }

    public Booking dtoToBooking(BookingDTO bookingDTO) {
        Booking booking = new Booking();
        booking.setBookingId(bookingDTO.getBookingId());
        booking.setDateOfBooking(bookingDTO.getDateOfBooking());

        if (bookingDTO.getFlightId() != null) {
            Flight flight = flightService.getFlightById(bookingDTO.getFlightId());
            if (flight == null) {
                throw new FlightNotFoundException("Flight not found with ID: " + bookingDTO.getFlightId());
            }
            booking.setFlight(flight);
        }

        if (bookingDTO.getPassengerId() != null) {
            Passenger passenger = passengerService.getPassengerById(bookingDTO.getPassengerId());
            if (passenger == null) {
                throw new PassengerNotFoundException("Passenger not found with ID: " + bookingDTO.getPassengerId());
            }
            booking.setPassenger(passenger);
        }

        if (bookingDTO.getSeatId() != null) {
            Seat seat = seatService.getSeatById(bookingDTO.getSeatId())
                    .orElseThrow(() -> new SeatNotFoundException("Seat not found with ID: " + bookingDTO.getSeatId()));
            booking.setSeat(seat);
        }

        if (bookingDTO.getUserId() != null) {
            User user = userService.getUserById(bookingDTO.getUserId());
            if (user == null) {
                throw new UserNotFoundException("User not found with ID: " + bookingDTO.getUserId());
            }
            booking.setUser(user);
        }

        return booking;
    }
}
