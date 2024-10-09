package com.flightbookings.flight_bookings.services;

import com.flightbookings.flight_bookings.models.*;
import com.flightbookings.flight_bookings.exceptions.*;
import com.flightbookings.flight_bookings.repositories.*;
import com.flightbookings.flight_bookings.services.interfaces.BookingService;
import com.flightbookings.flight_bookings.services.interfaces.SeatService;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

import java.util.List;
/**
 * Implementation of the BookingService interface for managing booking operations.
 */
@Service
public class BookingServiceImpl implements BookingService {

    private final IBookingRepository bookingRepository;
    private final ISeatRepository seatRepository;
    private final IFlightRepository flightRepository;
    private final IPassengerRepository passengerRepository;
    private final IUserRepository userRepository;
    private final SeatService seatService;

    /**
     * Constructs a BookingServiceImpl with the necessary repositories and services.
     *
     * @param bookingRepository   the repository for managing bookings.
     * @param seatRepository      the repository for managing seats.
     * @param flightRepository    the repository for managing flights.
     * @param passengerRepository the repository for managing passengers.
     * @param userRepository      the repository for managing users.
     * @param seatService         the service for managing seat operations.
     */
    public BookingServiceImpl(IBookingRepository bookingRepository, ISeatRepository seatRepository, IFlightRepository flightRepository, IPassengerRepository passengerRepository, IUserRepository userRepository, SeatService seatService) {
        this.bookingRepository = bookingRepository;
        this.seatRepository = seatRepository;
        this.flightRepository = flightRepository;
        this.passengerRepository = passengerRepository;
        this.userRepository = userRepository;
        this.seatService = seatService;
    }

    @Override
    public Booking createBooking(Long flightId, Long passengerId, String seatName, Long userId) {
        Flight flight = flightRepository.findById(flightId)
                .orElseThrow(() -> new FlightNotFoundException("Flight not found"));

        Passenger passenger = passengerRepository.findById(passengerId)
                .orElseThrow(() -> new PassengerNotFoundException("Passenger not found"));

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("User not found"));

        Seat seat = seatService.reserveSeat(flight, seatName);

        Booking booking = new Booking(null, LocalDateTime.now(), passenger, flight, seat, user);

        seat.setBooking(booking);

        bookingRepository.save(booking);

        return booking;
    }

    @Override
    public Booking updateBooking(Booking updatedBooking) {
        Optional<Booking> existingBookingOptional = bookingRepository.findById(updatedBooking.getBookingId());
        if (existingBookingOptional.isPresent()) {
            Booking existingBooking = existingBookingOptional.get();

            Seat previousSeat = existingBooking.getSeat();

            existingBooking.setDateOfBooking(updatedBooking.getDateOfBooking());
            existingBooking.setPassenger(updatedBooking.getPassenger());
            existingBooking.setFlight(updatedBooking.getFlight());

            existingBooking.setSeat(updatedBooking.getSeat());

            if (previousSeat != null) {
                previousSeat.setBooked(false);
                previousSeat.setBooking(null);
                seatRepository.save(previousSeat);
            }

            Seat newSeat = updatedBooking.getSeat();
            if (newSeat != null) {
                newSeat.setBooked(true);
                newSeat.setBooking(existingBooking);
                seatRepository.save(newSeat);
            }

            return bookingRepository.save(existingBooking);
        } else {
            throw new BookingNotFoundException("Booking not found with ID: " + updatedBooking.getBookingId());
        }
    }

    @Override
    public Booking getBookingByIdByUser(Long id, User user) {
        Optional<Booking> bookingOptional = bookingRepository.findById(id);
        if (bookingOptional.isPresent()) {
            Booking booking = bookingOptional.get();
            if (booking.getUser().equals(user)) {
                return booking;
            } else {
                throw new UnauthorizedAccessException("You do not have permission to view this booking.");
            }
        }
        return null;
    }

    @Override
    public List<Booking> getAllBookingsByUser(User user) {
        return bookingRepository.findByUser(user);
    }

    @Override
    public List<Booking> getAllBookings() {
        return bookingRepository.findAll();
    }

    @Override
    public boolean deleteBooking(Long id) {
        Optional<Booking> booking = bookingRepository.findById(id);
        if (booking.isPresent()) {
            bookingRepository.deleteById(id);
            return true;
        }
        return false;
    }
}
