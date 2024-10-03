package com.flightbookings.flight_bookings.services;

import com.flightbookings.flight_bookings.dtos.DTOBooking.BookingDTO;
import com.flightbookings.flight_bookings.exceptions.*;
import com.flightbookings.flight_bookings.models.*;
import com.flightbookings.flight_bookings.repositories.*;
import com.flightbookings.flight_bookings.services.interfaces.BookingService;
import com.flightbookings.flight_bookings.services.interfaces.SeatService;
import com.flightbookings.flight_bookings.dtos.DTOBooking.BookingConverter;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

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
    private final BookingConverter bookingConverter; // Added to convert between DTO and entity

    /**
     * Constructs a BookingServiceImpl with the necessary repositories and services.
     *
     * @param bookingRepository   the repository for managing bookings.
     * @param seatRepository      the repository for managing seats.
     * @param flightRepository    the repository for managing flights.
     * @param passengerRepository the repository for managing passengers.
     * @param userRepository      the repository for managing users.
     * @param seatService         the service for managing seat operations.
     * @param bookingConverter     the converter for converting between Booking and BookingDTO.
     */
    public BookingServiceImpl(IBookingRepository bookingRepository, ISeatRepository seatRepository,
                              IFlightRepository flightRepository, IPassengerRepository passengerRepository,
                              IUserRepository userRepository, SeatService seatService,
                              BookingConverter bookingConverter) {
        this.bookingRepository = bookingRepository;
        this.seatRepository = seatRepository;
        this.flightRepository = flightRepository;
        this.passengerRepository = passengerRepository;
        this.userRepository = userRepository;
        this.seatService = seatService;
        this.bookingConverter = bookingConverter; // Initialize the booking converter
    }

    @Override
    public BookingDTO createBooking(Long flightId, Long passengerId, String seatName, Long userId) {
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

        return bookingConverter.convertToDto(booking);
    }

    @Override
    public BookingDTO updateBooking(BookingDTO updatedBookingDTO) {
        Optional<Booking> existingBookingOptional = bookingRepository.findById(updatedBookingDTO.getBookingId());
        if (existingBookingOptional.isPresent()) {
            Booking existingBooking = existingBookingOptional.get();
            Seat previousSeat = existingBooking.getSeat();

            existingBooking.setDateOfBooking(updatedBookingDTO.getDateOfBooking());
            existingBooking.setPassenger(updatedBookingDTO.getPassenger());
            existingBooking.setFlight(updatedBookingDTO.getFlight());
            existingBooking.setSeat(updatedBookingDTO.getSeat());

            if (previousSeat != null) {
                previousSeat.setBooked(false);
                previousSeat.setBooking(null);
                seatRepository.save(previousSeat);
            }

            Seat newSeat = updatedBookingDTO.getSeat();
            if (newSeat != null) {
                newSeat.setBooked(true);
                newSeat.setBooking(existingBooking);
                seatRepository.save(newSeat);
            }

            return bookingConverter.convertToDto(bookingRepository.save(existingBooking)); // Return updated DTO
        } else {
            throw new BookingNotFoundException("Booking not found with ID: " + updatedBookingDTO.getBookingId());
        }
    }

    @Override
    public BookingDTO getBookingById(Long id, User user) {
        Optional<Booking> bookingOptional = bookingRepository.findById(id);
        if (bookingOptional.isPresent()) {
            Booking booking = bookingOptional.get();
            if (booking.getUser().equals(user)) {
                return bookingConverter.convertToDto(booking); // Convert to DTO before returning
            } else {
                throw new UnauthorizedAccessException("You do not have permission to view this booking.");
            }
        }
        return null; // Consider throwing a BookingNotFoundException instead
    }

    @Override
    public List<BookingDTO> getAllBookingsByUser(User user) {
        List<Booking> bookings = bookingRepository.findByUser(user);
        return bookings.stream()
                .map(bookingConverter::convertToDto)
                .toList(); // Convert each booking to DTO
    }

    @Override
    public List<BookingDTO> getAllBookings() {
        List<Booking> bookings = bookingRepository.findAll();
        return bookings.stream()
                .map(bookingConverter::convertToDto)
                .toList(); // Convert each booking to DTO
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
