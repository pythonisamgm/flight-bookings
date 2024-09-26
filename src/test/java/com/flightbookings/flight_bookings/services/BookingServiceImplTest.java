package com.flightbookings.flight_bookings.services;

import com.flightbookings.flight_bookings.exceptions.FlightNotFoundException;
import com.flightbookings.flight_bookings.exceptions.PassengerNotFoundException;
import com.flightbookings.flight_bookings.exceptions.SeatAlreadyBookedException;
import com.flightbookings.flight_bookings.exceptions.SeatNotFoundException;
import com.flightbookings.flight_bookings.models.Booking;
import com.flightbookings.flight_bookings.models.Flight;
import com.flightbookings.flight_bookings.models.Passenger;
import com.flightbookings.flight_bookings.models.Seat;
import com.flightbookings.flight_bookings.repositories.IBookingRepository;
import com.flightbookings.flight_bookings.repositories.IFlightRepository;
import com.flightbookings.flight_bookings.repositories.IPassengerRepository;
import com.flightbookings.flight_bookings.repositories.ISeatRepository;
import com.flightbookings.flight_bookings.services.interfaces.SeatService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class BookingServiceImplTest {

    @Mock
    private IBookingRepository bookingRepository;

    @Mock
    private IFlightRepository flightRepository;

    @Mock
    private IPassengerRepository passengerRepository;

    @Mock
    private ISeatRepository seatRepository;

    @Mock
    private SeatService seatService;

    @InjectMocks
    private BookingServiceImpl bookingService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void test_if_createBooking_creates_it_successfully() {
        Long flightId = 1L;
        Long passengerId = 1L;
        String seatName = "1A";

        Flight flight = new Flight();
        flight.setFlightId(flightId);

        Passenger passenger = new Passenger();
        passenger.setPassengerId(passengerId);

        Seat seat = new Seat();
        seat.setSeatName(seatName);

        when(flightRepository.findById(flightId)).thenReturn(Optional.of(flight));
        when(passengerRepository.findById(passengerId)).thenReturn(Optional.of(passenger));
        when(seatService.reserveSeat(flight, seatName)).thenReturn(seat);
        when(bookingRepository.save(any(Booking.class))).thenAnswer(invocation -> invocation.getArgument(0));

        Booking booking = bookingService.createBooking(flightId, passengerId, seatName);

        assertNotNull(booking);
        assertEquals(flight, booking.getFlight());
        assertEquals(passenger, booking.getPassenger());
        assertEquals(seat, booking.getSeat());
        assertNotNull(booking.getDateOfBooking());

        verify(flightRepository, times(1)).findById(flightId);
        verify(passengerRepository, times(1)).findById(passengerId);
        verify(seatService, times(1)).reserveSeat(flight, seatName);
        verify(bookingRepository, times(1)).save(any(Booking.class));
    }
    @Test
    void test_exception_for_flightNotFound() {
        Long flightId = 1L;
        Long passengerId = 1L;
        String seatName = "1A";

        when(flightRepository.findById(flightId)).thenReturn(Optional.empty());

        assertThrows(FlightNotFoundException.class, () -> {
            bookingService.createBooking(flightId, passengerId, seatName);
        });

        verify(flightRepository, times(1)).findById(flightId);
        verify(passengerRepository, times(0)).findById(anyLong());
        verify(seatService, times(0)).reserveSeat(any(Flight.class), anyString());
        verify(bookingRepository, times(0)).save(any(Booking.class));
    }
    @Test
    void test_exception_for_passengerNotFound() {
        Long flightId = 1L;
        Long passengerId = 1L;
        String seatName = "1A";

        Flight flight = new Flight();
        flight.setFlightId(flightId);

        when(flightRepository.findById(flightId)).thenReturn(Optional.of(flight));
        when(passengerRepository.findById(passengerId)).thenReturn(Optional.empty());

        assertThrows(PassengerNotFoundException.class, () -> {
            bookingService.createBooking(flightId, passengerId, seatName);
        });

        verify(flightRepository, times(1)).findById(flightId);
        verify(passengerRepository, times(1)).findById(passengerId);
        verify(seatService, times(0)).reserveSeat(any(Flight.class), anyString());
        verify(bookingRepository, times(0)).save(any(Booking.class));
    }
    @Test
    void test_update_method_basic_fields() {
        Long bookingId = 1L;
        Booking existingBooking = new Booking();
        existingBooking.setBookingId(bookingId);

        Booking updatedBooking = new Booking();
        updatedBooking.setBookingId(bookingId);
        updatedBooking.setDateOfBooking(LocalDateTime.now().plusDays(1));

        Passenger newPassenger = new Passenger();
        updatedBooking.setPassenger(newPassenger);

        Flight newFlight = new Flight();
        updatedBooking.setFlight(newFlight);

        when(bookingRepository.findById(bookingId)).thenReturn(Optional.of(existingBooking));
        when(bookingRepository.save(any(Booking.class))).thenAnswer(invocation -> invocation.getArgument(0));

        Booking result = bookingService.updateBooking(updatedBooking);

        assertNotNull(result);
        assertEquals(newPassenger, result.getPassenger());
        assertEquals(newFlight, result.getFlight());
        assertEquals(updatedBooking.getDateOfBooking(), result.getDateOfBooking());

        verify(bookingRepository, times(1)).findById(bookingId);
        verify(bookingRepository, times(1)).save(existingBooking);
    }
    @Test
    public void testUpdateBooking_WhenBookingExists_ShouldUpdateBookingAndFreePreviousSeat() {
        Long bookingId = 1L;
        Booking existingBooking = new Booking();
        existingBooking.setBookingId(bookingId);

        Seat previousSeat = new Seat();
        previousSeat.setBooked(true);
        existingBooking.setSeat(previousSeat);

        Booking updatedBooking = new Booking();
        updatedBooking.setBookingId(bookingId);

        Seat newSeat = new Seat();
        newSeat.setBooked(false);
        updatedBooking.setSeat(newSeat);

        when(bookingRepository.findById(bookingId)).thenReturn(Optional.of(existingBooking));
        when(seatRepository.save(any(Seat.class))).thenAnswer(invocation -> invocation.getArgument(0));
        when(bookingRepository.save(any(Booking.class))).thenAnswer(invocation -> invocation.getArgument(0));

        Booking result = bookingService.updateBooking(updatedBooking);

        assertNotNull(result);
        assertEquals(updatedBooking.getBookingId(), result.getBookingId());
        assertEquals(newSeat, result.getSeat());
        assertTrue(newSeat.isBooked());
        assertFalse(previousSeat.isBooked());
        verify(seatRepository, times(1)).save(previousSeat);
        verify(seatRepository, times(1)).save(newSeat);
        verify(bookingRepository, times(1)).save(existingBooking);
    }

    @Test
    public void testUpdateBooking_WhenBookingDoesNotExist_ShouldThrowException() {
        Long bookingId = 1L;
        Booking updatedBooking = new Booking();
        updatedBooking.setBookingId(bookingId);

        when(bookingRepository.findById(bookingId)).thenReturn(Optional.empty());

        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            bookingService.updateBooking(updatedBooking);
        });

        assertEquals("Booking not found with ID: " + bookingId, exception.getMessage());
        verify(bookingRepository, never()).save(any(Booking.class));
    }






}