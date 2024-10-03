package com.flightbookings.flight_bookings.services;

import com.flightbookings.flight_bookings.dtos.DTOBooking.BookingDTO;
import com.flightbookings.flight_bookings.dtos.DTOSeat.SeatDTO;
import com.flightbookings.flight_bookings.dtos.DTOUser.UserDTO;
import com.flightbookings.flight_bookings.exceptions.*;
import com.flightbookings.flight_bookings.models.*;
import com.flightbookings.flight_bookings.repositories.IBookingRepository;
import com.flightbookings.flight_bookings.repositories.IFlightRepository;
import com.flightbookings.flight_bookings.repositories.IPassengerRepository;
import com.flightbookings.flight_bookings.repositories.ISeatRepository;
import com.flightbookings.flight_bookings.services.interfaces.BookingService;
import com.flightbookings.flight_bookings.services.interfaces.SeatService;
import com.flightbookings.flight_bookings.dtos.DTOBooking.BookingConverter;
import com.flightbookings.flight_bookings.dtos.DTOSeat.SeatConverter;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class BookingServiceImpl implements BookingService {

    private final IBookingRepository bookingRepository;
    private final ISeatRepository seatRepository;
    private final IFlightRepository flightRepository;
    private final IPassengerRepository passengerRepository;
    private final SeatService seatService;
    private final BookingConverter bookingConverter;
    private final SeatConverter seatConverter;

    public BookingServiceImpl(IBookingRepository bookingRepository, ISeatRepository seatRepository,
                              IFlightRepository flightRepository, IPassengerRepository passengerRepository,
                              SeatService seatService, BookingConverter bookingConverter, SeatConverter seatConverter) {
        this.bookingRepository = bookingRepository;
        this.seatRepository = seatRepository;
        this.flightRepository = flightRepository;
        this.passengerRepository = passengerRepository;
        this.seatService = seatService;
        this.bookingConverter = bookingConverter;
        this.seatConverter = seatConverter;
    }

    @Override
    public BookingDTO createBooking(Long flightId, Long passengerId, String seatName, User user) {
        Flight flight = flightRepository.findById(flightId)
                .orElseThrow(() -> new FlightNotFoundException("Flight not found"));

        Passenger passenger = passengerRepository.findById(passengerId)
                .orElseThrow(() -> new PassengerNotFoundException("Passenger not found"));

        SeatDTO seatDTO = seatService.reserveSeat(flight, seatName);

        Seat seat = seatConverter.convertToEntity(seatDTO);

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
            Booking updatedBooking = bookingConverter.convertToEntity(updatedBookingDTO);

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

            bookingRepository.save(existingBooking);

            return bookingConverter.convertToDto(existingBooking);
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
                return bookingConverter.convertToDto(booking);
            } else {
                throw new UnauthorizedAccessException("You do not have permission to view this booking.");
            }
        }
        return null;
    }

    @Override
    public List<BookingDTO> getAllBookings(UserDTO user) {
        List<Booking> bookings = bookingRepository.findByUser(user);
        return bookings.stream()
                .map(bookingConverter::convertToDto)
                .collect(Collectors.toList());
    }

    @Override
    public BookingDTO updateBooking2(Long id, BookingDTO bookingDetailsDTO) {
        Optional<Booking> existingBooking = bookingRepository.findById(id);
        if (existingBooking.isPresent()) {
            Booking bookingToUpdate = existingBooking.get();
            Booking updatedBooking = bookingConverter.convertToEntity(bookingDetailsDTO);

            bookingToUpdate.setFlight(updatedBooking.getFlight());
            bookingToUpdate.setPassenger(updatedBooking.getPassenger());
            bookingToUpdate.setDateOfBooking(updatedBooking.getDateOfBooking());

            bookingRepository.save(bookingToUpdate);

            return bookingConverter.convertToDto(bookingToUpdate);
        }
        return null;
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
