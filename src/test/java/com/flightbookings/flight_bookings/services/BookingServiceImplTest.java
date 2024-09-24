package com.flightbookings.flight_bookings.services;

import com.flightbookings.flight_bookings.models.Booking;
import com.flightbookings.flight_bookings.repositories.IBookingRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class BookingServiceImplTest {

    @Mock
    private IBookingRepository bookingRepository;

    @InjectMocks
    private BookingServiceImpl bookingService;

    private Booking booking;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        booking = new Booking();
        booking.setBookingId(1L);
    }

    @Test
    public void CreateBookingTest() {
        when(bookingRepository.save(booking)).thenReturn(booking);
        Booking savedBooking = bookingService.createBooking(booking);
        assertNotNull(savedBooking);
        assertEquals(1L, savedBooking.getBookingId());
        verify(bookingRepository, times(1)).save(booking);
    }

    @Test
    public void GetBookingByIdTest() {
        when(bookingRepository.findById(1L)).thenReturn(Optional.of(booking));
        Booking foundBooking = bookingService.getBookingById(1L);
        assertNotNull(foundBooking);
        assertEquals(1L, foundBooking.getBookingId());
        verify(bookingRepository, times(1)).findById(1L);
    }

    @Test
    public void GetAllBookingsTest() {
        when(bookingRepository.findAll()).thenReturn(Arrays.asList(booking));
        List<Booking> bookings = bookingService.getAllBookings();
        assertEquals(1, bookings.size());
        verify(bookingRepository, times(1)).findAll();
    }

    @Test
    public void DeleteBookingTest() {
        when(bookingRepository.findById(1L)).thenReturn(Optional.of(booking));
        boolean isDeleted = bookingService.deleteBooking(1L);
        assertTrue(isDeleted);
        verify(bookingRepository, times(1)).deleteById(1L);
    }

    @Test
    public void UpdateBookingTest() {
        when(bookingRepository.findById(1L)).thenReturn(Optional.of(booking));
        when(bookingRepository.save(booking)).thenReturn(booking);
        Booking updatedBooking = bookingService.updateBooking(1L, booking);
        assertNotNull(updatedBooking);
        verify(bookingRepository, times(1)).save(booking);
    }
}