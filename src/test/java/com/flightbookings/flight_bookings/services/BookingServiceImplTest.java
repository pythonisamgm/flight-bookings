package com.flightbookings.flight_bookings.services;

import com.flightbookings.flight_bookings.models.Booking;
import com.flightbookings.flight_bookings.repositories.IBookingRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class BookingServiceImplTest {

    @Mock
    private IBookingRepository bookingRepository;

    @InjectMocks
    private BookingServiceImpl bookingService;

    private Booking booking1;
    private Booking booking2;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);

        booking1 = new Booking();
        booking1.setBookingId(1L);
        booking1.setDateOfBooking(LocalDateTime.of(2024, 9, 24, 10, 0));


        booking2 = new Booking();
        booking2.setBookingId(2L);
        booking2.setDateOfBooking(LocalDateTime.of(2024, 9, 25, 11, 0));

    }

    @Test
    public void testCreateBooking() {
        when(bookingRepository.save(any(Booking.class))).thenReturn(booking1);

        Booking createdBooking = bookingService.createBooking(booking1);

        assertNotNull(createdBooking);
        assertEquals(1L, createdBooking.getBookingId());
        assertEquals(LocalDateTime.of(2024, 9, 24, 10, 0), createdBooking.getDateOfBooking());

        verify(bookingRepository, times(1)).save(any(Booking.class));
    }

    @Test
    public void testGetBookingById() {
        when(bookingRepository.findById(1L)).thenReturn(Optional.of(booking1));

        Booking foundBooking = bookingService.getBookingById(1L);

        assertNotNull(foundBooking);
        assertEquals(1L, foundBooking.getBookingId());

        verify(bookingRepository, times(1)).findById(1L);
    }

    @Test
    public void testGetBookingById_NotFound() {
        when(bookingRepository.findById(3L)).thenReturn(Optional.empty());

        Booking foundBooking = bookingService.getBookingById(3L);

        assertNull(foundBooking);

        verify(bookingRepository, times(1)).findById(3L);
    }

    @Test
    public void testGetAllBookings() {
        List<Booking> bookings = Arrays.asList(booking1, booking2);
        when(bookingRepository.findAll()).thenReturn(bookings);

        List<Booking> allBookings = bookingService.getAllBookings();

        assertEquals(2, allBookings.size());
        assertEquals(1L, allBookings.get(0).getBookingId());
        assertEquals(2L, allBookings.get(1).getBookingId());

        verify(bookingRepository, times(1)).findAll();
    }

    @Test
    public void testUpdateBooking() {
        when(bookingRepository.findById(1L)).thenReturn(Optional.of(booking1));
        when(bookingRepository.save(any(Booking.class))).thenReturn(booking1);

        Booking updatedBooking = bookingService.updateBooking(1L, booking1);

        assertNotNull(updatedBooking);
        assertEquals(1L, updatedBooking.getBookingId());

        verify(bookingRepository, times(1)).findById(1L);
        verify(bookingRepository, times(1)).save(any(Booking.class));
    }

    @Test
    public void testUpdateBooking_NotFound() {
        when(bookingRepository.findById(3L)).thenReturn(Optional.empty());

        Booking updatedBooking = bookingService.updateBooking(3L, booking1);

        assertNull(updatedBooking);

        verify(bookingRepository, times(1)).findById(3L);
        verify(bookingRepository, times(0)).save(any(Booking.class));
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