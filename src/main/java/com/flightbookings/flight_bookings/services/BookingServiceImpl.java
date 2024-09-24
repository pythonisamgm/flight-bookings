package com.flightbookings.flight_bookings.services;

import com.flightbookings.flight_bookings.exceptions.FlightNotFoundException;
import com.flightbookings.flight_bookings.exceptions.PassengerNotFoundException;
import com.flightbookings.flight_bookings.models.Booking;
import com.flightbookings.flight_bookings.models.Flight;
import com.flightbookings.flight_bookings.models.Passenger;
import com.flightbookings.flight_bookings.models.Seat;
import com.flightbookings.flight_bookings.repositories.IBookingRepository;
import com.flightbookings.flight_bookings.repositories.IFlightRepository;
import com.flightbookings.flight_bookings.repositories.IPassengerRepository;
import com.flightbookings.flight_bookings.repositories.ISeatRepository;
import com.flightbookings.flight_bookings.services.interfaces.BookingService;
import com.flightbookings.flight_bookings.services.interfaces.SeatService;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class BookingServiceImpl implements BookingService {

    private final IBookingRepository bookingRepository;
    private final ISeatRepository seatRepository;
    private final IFlightRepository flightRepository;
    private final IPassengerRepository passengerRepository;
    private final SeatService seatService;

    public BookingServiceImpl(IBookingRepository bookingRepository, ISeatRepository seatRepository, IFlightRepository flightRepository, IPassengerRepository passengerRepository, SeatService seatService) {
        this.bookingRepository = bookingRepository;
        this.seatRepository = seatRepository;
        this.flightRepository = flightRepository;
        this.passengerRepository = passengerRepository;
        this.seatService = seatService;
    }
   @Override
    public Booking createBooking(Long flightId, Long passengerId, String seatName) {
        Flight flight = flightRepository.findById(flightId)
                .orElseThrow(() -> new FlightNotFoundException("Flight not found"));

        Passenger passenger = passengerRepository.findById(passengerId)
                .orElseThrow(() -> new PassengerNotFoundException("Passenger not found"));

        Seat seat = seatService.reserveSeat(flight, seatName);

        Booking booking = new Booking( null, LocalDateTime.now(),passenger, flight,  seat);
        seat.setBooking(booking);

        bookingRepository.save(booking);

        return booking;
    }

    @Override
    public Booking updateBooking(Booking updatedBooking) {
        Optional<Booking> existingBookingOptional = bookingRepository.findById(updatedBooking.getBookingId());
        if (existingBookingOptional.isPresent()) {
            Booking existingBooking = existingBookingOptional.get();

            existingBooking.setDateOfBooking(updatedBooking.getDateOfBooking());
            existingBooking.setPassenger(updatedBooking.getPassenger());
            existingBooking.setFlight(updatedBooking.getFlight());

            if (updatedBooking.getSeat() != null && !updatedBooking.getSeat().equals(existingBooking.getSeat())) {
                Seat currentSeat = existingBooking.getSeat();
                currentSeat.setBooked(false);
                currentSeat.setBooking(null);
                seatRepository.save(currentSeat);

                Flight flight = existingBooking.getFlight();
                Seat newSeat = seatService.reserveSeat(flight, updatedBooking.getSeat().getSeatName());

                existingBooking.setSeat(newSeat);
                newSeat.setBooking(existingBooking);
            }

            return bookingRepository.save(existingBooking);
        }
        return null;
    }
}
