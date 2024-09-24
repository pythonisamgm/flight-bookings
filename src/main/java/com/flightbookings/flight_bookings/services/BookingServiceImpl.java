package com.flightbookings.flight_bookings.services;

import com.flightbookings.flight_bookings.exceptions.BookingNotFoundException;
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
    public Booking changeSeat(Long bookingId, String newSeatName) {
        Booking booking = bookingRepository.findById(bookingId)
                .orElseThrow(() -> new BookingNotFoundException("Booking not found"));

        Seat currentSeat = booking.getSeat();
        Flight flight = booking.getFlight();

        // Liberar el asiento actual
        currentSeat.setBooked(false);
        currentSeat.setBooking(null);
        seatRepository.save(currentSeat);

        // Reservar el nuevo asiento
        Seat newSeat = seatService.reserveSeat(flight, newSeatName);

        // Actualizar la reserva con el nuevo asiento
        booking.setSeat(newSeat);
        newSeat.setBooking(booking);

        bookingRepository.save(booking);

        return booking;
    }

}
