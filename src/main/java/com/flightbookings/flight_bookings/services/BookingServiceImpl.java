package com.flightbookings.flight_bookings.services;

import com.flightbookings.flight_bookings.models.*;
import com.flightbookings.flight_bookings.exceptions.*;
import com.flightbookings.flight_bookings.repositories.*;
import com.flightbookings.flight_bookings.services.interfaces.BookingService;
import com.flightbookings.flight_bookings.services.interfaces.SeatService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

import java.util.List;
/**
 * Implementation of the BookingService interface for managing booking operations.
 */
@Service
public class BookingServiceImpl implements BookingService {

    private IBookingRepository bookingRepository;
    private ISeatRepository seatRepository;
    private IFlightRepository flightRepository;
    private IPassengerRepository passengerRepository;
    private IUserRepository userRepository;
    private SeatService seatService;

    @Autowired
    public void setBookingRepository(IBookingRepository bookingRepository) {
        this.bookingRepository = bookingRepository;
    }

    @Autowired
    public void setSeatRepository(ISeatRepository seatRepository) {
        this.seatRepository = seatRepository;
    }

    @Autowired
    public void setFlightRepository(IFlightRepository flightRepository) {
        this.flightRepository = flightRepository;
    }

    @Autowired
    public void setPassengerRepository(IPassengerRepository passengerRepository) {
        this.passengerRepository = passengerRepository;
    }

    @Autowired
    public void setUserRepository(IUserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Lazy
    @Autowired
    public void setSeatService(SeatService seatService) {
        this.seatService = seatService;
    }



    @Override
    public BookingEntity createBooking(Long flightId, Long passengerId, String seatName, Long userId) {
        FlightEntity flight = flightRepository.findById(flightId)
                .orElseThrow(() -> new FlightNotFoundException("Flight not found"));

        PassengerEntity passenger = passengerRepository.findById(passengerId)
                .orElseThrow(() -> new PassengerNotFoundException("Passenger not found"));

        UserEntity user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("User not found"));

        SeatEntity seat = seatService.reserveSeat(flight, seatName);

        BookingEntity booking = new BookingEntity(null, LocalDateTime.now(), passenger, flight, seat, user);

        seat.setBooking(booking);

        bookingRepository.save(booking);

        return booking;
    }

    @Override
    public BookingEntity getBookingByIdByUser(Long id, UserEntity user) {
        Optional<BookingEntity> bookingOptional = bookingRepository.findById(id);
        if (bookingOptional.isPresent()) {
            BookingEntity booking = bookingOptional.get();
            if (booking.getUser().equals(user)) {
                return booking;
            } else {
                throw new UnauthorizedAccessException("You do not have permission to view this booking.");
            }
        }
        return null;
    }

    @Override
    public List<BookingEntity> getAllBookingsByUser(UserEntity user) {
        return bookingRepository.findByUser(user);
    }

    @Override
    public List<BookingEntity> getAllBookings() {
        return bookingRepository.findAll();
    }

    @Override
    public BookingEntity updateBooking(BookingEntity updatedBooking) {
        Optional<BookingEntity> existingBookingOptional = bookingRepository.findById(updatedBooking.getBookingId());
        if (existingBookingOptional.isPresent()) {
            BookingEntity existingBooking = existingBookingOptional.get();

            SeatEntity previousSeat = existingBooking.getSeat();

            existingBooking.setDateOfBooking(updatedBooking.getDateOfBooking());
            existingBooking.setPassenger(updatedBooking.getPassenger());
            existingBooking.setFlight(updatedBooking.getFlight());

            existingBooking.setSeat(updatedBooking.getSeat());

            if (previousSeat != null) {
                previousSeat.setBooked(false);
                previousSeat.setBooking(null);
                seatRepository.save(previousSeat);
            }

            SeatEntity newSeat = updatedBooking.getSeat();
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
    public boolean deleteBooking(Long id) {
        Optional<BookingEntity> booking = bookingRepository.findById(id);
        if (booking.isPresent()) {
            bookingRepository.deleteById(id);
            return true;
        }
        return false;
    }
}
