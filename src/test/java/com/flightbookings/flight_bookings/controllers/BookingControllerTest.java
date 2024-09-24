package com.flightbookings.flight_bookings.controllers;

import com.flightbookings.flight_bookings.models.Booking;
import com.flightbookings.flight_bookings.services.BookingServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class BookingControllerTest {

    @Mock
    private BookingServiceImpl bookingService;

    @InjectMocks
    private BookingController bookingController;

    private Booking booking1;
    private Booking booking2;
    private List<Booking> bookingList;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);

        booking1 = new Booking();
        booking1.setBookingId(1L);

        booking2 = new Booking();
        booking2.setBookingId(2L);

        bookingList = new ArrayList<>();
        bookingList.add(booking1);
        bookingList.add(booking2);
    }

    @Test
    public void CreateBookingTest() {
        when(bookingService.createBooking(booking1)).thenReturn(booking1);
        ResponseEntity<Booking> response = bookingController.createBooking(booking1);
        assertNotNull(response.getBody());
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(1L, response.getBody().getBookingId());
        verify(bookingService, times(1)).createBooking(booking1);
    }

    @Test
    public void GetBookingByIdTest() {
        when(bookingService.getBookingById(1L)).thenReturn(booking1);
        ResponseEntity<Booking> response = bookingController.getBookingById(1L);
        assertNotNull(response.getBody());
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(1L, response.getBody().getBookingId());
        verify(bookingService, times(1)).getBookingById(1L);
    }

    @Test
    public void GetAllBookingsTest() {
        when(bookingService.getAllBookings()).thenReturn(bookingList);
        ResponseEntity<List<Booking>> response = bookingController.getAllBookings();
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(2, response.getBody().size());
        verify(bookingService, times(1)).getAllBookings();
    }

    @Test
    public void UpdateBookingTest() {
        when(bookingService.updateBooking(2L, booking2)).thenReturn(booking2);
        ResponseEntity<Booking> response = bookingController.updateBooking(2L, booking2);
        assertNotNull(response.getBody());
        assertEquals(HttpStatus.OK, response.getStatusCode());
        verify(bookingService, times(1)).updateBooking(2L, booking2);
    }

    @Test
    public void DeleteBookingTest() {
        when(bookingService.deleteBooking(1L)).thenReturn(true);
        ResponseEntity<Void> response = bookingController.deleteBooking(1L);
        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        verify(bookingService, times(1)).deleteBooking(1L);
    }
}
