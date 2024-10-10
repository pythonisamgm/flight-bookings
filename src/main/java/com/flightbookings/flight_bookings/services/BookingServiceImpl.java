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
 * Implementation of the {@link BookingService} interface for managing booking operations.
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
    public BookingServiceImpl(IBookingRepository bookingRepository, ISeatRepository seatRepository,
                              IFlightRepository flightRepository, IPassengerRepository passengerRepository,
                              IUserRepository userRepository, SeatService seatService) {
        this.bookingRepository = bookingRepository;
        this.seatRepository = seatRepository;
        this.flightRepository = flightRepository;
        this.passengerRepository = passengerRepository;
        this.userRepository = userRepository;
        this.seatService = seatService;
    }

    /**
     * Creates a new booking for a specified flight, passenger, seat, and user.
     *
     * @param flightId    the ID of the flight.
     * @param passengerId the ID of the passenger.
     * @param seatName    the name of the seat to reserve.
     * @param userId      the ID of the user making the booking.
     * @return the created booking.
     * @throws FlightNotFoundException if the flight with the given ID does not exist.
     * @throws PassengerNotFoundException if the passenger with the given ID does not exist.
     * @throws UserNotFoundException if the user with the given ID does not exist.
     */
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

    /**
     * Updates an existing booking with new details.
     *
     * @param updatedBooking the booking containing the updated information.
     * @return the updated booking.
     * @throws BookingNotFoundException if the booking with the given ID does not exist.
     */
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

    /**
     * Creates a new booking directly from a Booking object.
     *
     * @param booking the booking object containing details for the new booking.
     * @return the saved booking.
     */
    @Override
    public Booking createBooking2(Booking booking) {
        return bookingRepository.save(booking);
    }

    /**
     * Retrieves a booking by ID, checking if the user has permission to view it.
     *
     * @param id   the ID of the booking to retrieve.
     * @param user the user attempting to view the booking.
     * @return the booking if found and the user has access; otherwise, throws an exception.
     * @throws UnauthorizedAccessException if the user does not have permission to view the booking.
     */
    @Override
    public Booking getBookingById(Long id, User user) {
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

    /**
     * Retrieves all bookings for a specific user.
     *
     * @param user the user whose bookings are to be retrieved.
     * @return a list of bookings associated with the user.
     */
    @Override
    public List<Booking> getAllBookingsByUser(User user) {
        return bookingRepository.findByUser(user);
    }

    /**
     * Retrieves all bookings in the system.
     *
     * @return a list of all bookings.
     */
    @Override
    public List<Booking> getAllBookings() {
        return bookingRepository.findAll();
    }


    /**
     * Deletes a booking by ID.
     *
     * @param id the ID of the booking to delete.
     * @return true if the booking was successfully deleted; false otherwise.
     */
    @Override
    public boolean deleteBooking(Long id) {
        Optional<Booking> booking = bookingRepository.findById(id);
        if (booking.isPresent()) {
            bookingRepository.deleteById(id);
            return true;
        }
        return false;
    }
    @Override
    public Optional<Booking> getGeneralBookingById(Long id) {
        return bookingRepository.findById(id);
    }
}
