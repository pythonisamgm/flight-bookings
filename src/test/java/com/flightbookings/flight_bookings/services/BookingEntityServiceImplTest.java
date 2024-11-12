package com.flightbookings.flight_bookings.services;

import com.flightbookings.flight_bookings.models.*;
import com.flightbookings.flight_bookings.repositories.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import com.flightbookings.flight_bookings.exceptions.FlightNotFoundException;
import com.flightbookings.flight_bookings.exceptions.PassengerNotFoundException;
import com.flightbookings.flight_bookings.services.interfaces.SeatService;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class BookingEntityServiceImplTest {

    @Mock
    private IBookingRepository bookingRepository;

    @Mock
    private IFlightRepository flightRepository;

    @Mock
    private IPassengerRepository passengerRepository;

    @Mock
    private ISeatRepository seatRepository;
    @Mock
    private IUserRepository userRepository;



    @Mock
    private SeatService seatService;

    @InjectMocks
    private BookingServiceImpl bookingService;


    private BookingEntity booking1;
    private BookingEntity booking2;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);

        PassengerEntity passenger1 = new PassengerEntity();
        passenger1.setPassengerId(1L);
        FlightEntity flight1 = new FlightEntity();
        flight1.setFlightId(1L);
        SeatEntity seat1 = new SeatEntity();
        seat1.setSeatName("1A");
        UserEntity user1 = new UserEntity();
        user1.setUserId(1L);

        PassengerEntity passenger2 = new PassengerEntity();
        passenger2.setPassengerId(2L);
        FlightEntity flight2 = new FlightEntity();
        flight2.setFlightId(2L);
        SeatEntity seat2 = new SeatEntity();
        seat2.setSeatName("2B");
        UserEntity user2 = new UserEntity();
        user2.setUserId(2L);

        booking1 = new BookingEntity(1L, LocalDateTime.of(2024, 9, 24, 10, 0), passenger1, flight1, seat1, user1);
        booking2 = new BookingEntity(2L, LocalDateTime.of(2024, 9, 25, 11, 0), passenger2, flight2, seat2, user2);
    }


    @Test
    void test_if_createBooking_creates_it_successfully() {
        Long flightId = 1L;
        Long passengerId = 1L;
        String seatName = "1A";
        Long userId = 1L;

        FlightEntity flight = new FlightEntity();
        flight.setFlightId(flightId);

        PassengerEntity passenger = new PassengerEntity();
        passenger.setPassengerId(passengerId);

        SeatEntity seat = new SeatEntity();
        seat.setSeatName(seatName);

        UserEntity user = new UserEntity();
        user.setUserId(userId);

        when(flightRepository.findById(flightId)).thenReturn(Optional.of(flight));
        when(passengerRepository.findById(passengerId)).thenReturn(Optional.of(passenger));
        when(seatService.reserveSeat(flight, seatName)).thenReturn(seat);
        when(userRepository.findById(userId)).thenReturn(Optional.of(user));
        when(bookingRepository.save(any(BookingEntity.class))).thenAnswer(invocation -> invocation.getArgument(0));

        BookingEntity booking = bookingService.createBooking(flightId, passengerId, seatName, userId);

        assertNotNull(booking);
        assertEquals(flight, booking.getFlight());
        assertEquals(passenger, booking.getPassenger());
        assertEquals(seat, booking.getSeat());
        assertEquals(user, booking.getUser());
        assertNotNull(booking.getDateOfBooking());

        verify(flightRepository, times(1)).findById(flightId);
        verify(passengerRepository, times(1)).findById(passengerId);
        verify(seatService, times(1)).reserveSeat(flight, seatName);
        verify(userRepository, times(1)).findById(userId);
        verify(bookingRepository, times(1)).save(any(BookingEntity.class));
    }

    @Test
    void test_exception_for_flightNotFound() {
        Long flightId = 1L;
        Long passengerId = 1L;
        String seatName = "1A";
        Long userId = 1L;

        when(flightRepository.findById(flightId)).thenReturn(Optional.empty());

        assertThrows(FlightNotFoundException.class, () -> {
            bookingService.createBooking(flightId, passengerId, seatName, userId);
        });

        verify(flightRepository, times(1)).findById(flightId);
        verify(passengerRepository, times(0)).findById(anyLong());
        verify(seatService, times(0)).reserveSeat(any(FlightEntity.class), anyString());
        verify(bookingRepository, times(0)).save(any(BookingEntity.class));
    }

    @Test
    void test_exception_for_passengerNotFound() {
        Long flightId = 1L;
        Long passengerId = 1L;
        String seatName = "1A";
        Long userId = 1L;

        FlightEntity flight = new FlightEntity();
        flight.setFlightId(flightId);

        when(flightRepository.findById(flightId)).thenReturn(Optional.of(flight));
        when(passengerRepository.findById(passengerId)).thenReturn(Optional.empty());

        assertThrows(PassengerNotFoundException.class, () -> {
            bookingService.createBooking(flightId, passengerId, seatName, userId);
        });

        verify(flightRepository, times(1)).findById(flightId);
        verify(passengerRepository, times(1)).findById(passengerId);
        verify(seatService, times(0)).reserveSeat(any(FlightEntity.class), anyString());
        verify(bookingRepository, times(0)).save(any(BookingEntity.class));
    }

    @Test
    public void testGetBookingByIdByUser() {
        Long bookingId = 1L;
        UserEntity user = new UserEntity();
        user.setUserId(1L);

        booking1.setUser(user);

        when(bookingRepository.findById(bookingId)).thenReturn(Optional.of(booking1));

        BookingEntity foundBooking = bookingService.getBookingByIdByUser(bookingId, user);

        assertNotNull(foundBooking);
        assertEquals(bookingId, foundBooking.getBookingId());

        assertNotNull(foundBooking);
        assertEquals(bookingId, foundBooking.getBookingId());

        verify(bookingRepository, times(1)).findById(bookingId);
    }

    @Test
    public void testGetBookingById_ByUser_NotFound() {
        Long bookingId = 3L;
        UserEntity user = new UserEntity();
        user.setUserId(1L);
        when(bookingRepository.findById(3L)).thenReturn(Optional.empty());

        BookingEntity foundBooking = bookingService.getBookingByIdByUser(3L, user);

        assertNull(foundBooking);

        verify(bookingRepository, times(1)).findById(3L);
    }

    @Test
    public void testGetAllBookings() {
        List<BookingEntity> bookings = Arrays.asList(booking1, booking2);
        when(bookingRepository.findAll()).thenReturn(bookings);

        List<BookingEntity> allBookings = bookingService.getAllBookings();

        assertEquals(2, allBookings.size());
        assertEquals(1L, allBookings.get(0).getBookingId());
        assertEquals(2L, allBookings.get(1).getBookingId());

        verify(bookingRepository, times(1)).findAll();
    }
    @Test
    public void testGetAllBookingsByUser() {
        UserEntity user = new UserEntity();
        user.setUserId(1L);

        List<BookingEntity> bookings = Arrays.asList(booking1, booking2);

        when(bookingRepository.findByUser(user)).thenReturn(bookings);

        List<BookingEntity> allBookings = bookingService.getAllBookingsByUser(user);

        assertEquals(2, allBookings.size());
        assertEquals(1L, allBookings.get(0).getBookingId());
        assertEquals(2L, allBookings.get(1).getBookingId());

        verify(bookingRepository, times(1)).findByUser(user);
    }

    @Test
    void test_update_method_basic_fields() {
        Long bookingId = 1L;
        Long userId = 1L;

        BookingEntity existingBooking = new BookingEntity();
        existingBooking.setBookingId(bookingId);

        BookingEntity updatedBooking = new BookingEntity();
        updatedBooking.setBookingId(bookingId);
        updatedBooking.setDateOfBooking(LocalDateTime.now().plusDays(1));

        PassengerEntity newPassenger = new PassengerEntity();
        updatedBooking.setPassenger(newPassenger);

        FlightEntity newFlight = new FlightEntity();
        updatedBooking.setFlight(newFlight);

        UserEntity newUser = new UserEntity();
        newUser.setUserId(userId);

        when(bookingRepository.findById(bookingId)).thenReturn(Optional.of(existingBooking));
        when(userRepository.findById(userId)).thenReturn(Optional.of(newUser));
        when(bookingRepository.save(any(BookingEntity.class))).thenAnswer(invocation -> invocation.getArgument(0));

        BookingEntity result = bookingService.updateBooking(updatedBooking);

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
        BookingEntity existingBooking = new BookingEntity();
        existingBooking.setBookingId(bookingId);

        SeatEntity previousSeat = new SeatEntity();
        previousSeat.setBooked(true);
        existingBooking.setSeat(previousSeat);

        BookingEntity updatedBooking = new BookingEntity();
        updatedBooking.setBookingId(bookingId);

        SeatEntity newSeat = new SeatEntity();
        newSeat.setBooked(false);
        updatedBooking.setSeat(newSeat);

        when(bookingRepository.findById(bookingId)).thenReturn(Optional.of(existingBooking));
        when(seatRepository.save(any(SeatEntity.class))).thenAnswer(invocation -> invocation.getArgument(0));
        when(bookingRepository.save(any(BookingEntity.class))).thenAnswer(invocation -> invocation.getArgument(0));

        BookingEntity result = bookingService.updateBooking(updatedBooking);

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
        BookingEntity updatedBooking = new BookingEntity();
        updatedBooking.setBookingId(bookingId);

        when(bookingRepository.findById(bookingId)).thenReturn(Optional.empty());

        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            bookingService.updateBooking(updatedBooking);
        });

        assertEquals("Booking not found with ID: " + bookingId, exception.getMessage());
        verify(bookingRepository, never()).save(any(BookingEntity.class));
    }

    @Test
    public void testDeleteBooking() {
        when(bookingRepository.findById(1L)).thenReturn(Optional.of(booking1));
        doNothing().when(bookingRepository).deleteById(1L);

        boolean isDeleted = bookingService.deleteBooking(1L);

        assertTrue(isDeleted);
        verify(bookingRepository, times(1)).findById(1L);
        verify(bookingRepository, times(1)).deleteById(1L);
    }

    @Test
    public void testDeleteBooking_NotFound() {
        when(bookingRepository.findById(3L)).thenReturn(Optional.empty());

        boolean isDeleted = bookingService.deleteBooking(3L);

        assertFalse(isDeleted);
        verify(bookingRepository, times(1)).findById(3L);
        verify(bookingRepository, times(0)).deleteById(anyLong());
    }
}




