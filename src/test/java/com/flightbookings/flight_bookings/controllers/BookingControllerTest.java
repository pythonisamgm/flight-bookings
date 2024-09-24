package com.flightbookings.flight_bookings.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.flightbookings.flight_bookings.models.Booking;
import com.flightbookings.flight_bookings.services.BookingServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class BookingControllerTest {

    @Mock
    private BookingServiceImpl bookingService;

    @InjectMocks
    private BookingController bookingController;

    private MockMvc mockMvc;

    private Booking booking1;
    private Booking booking2;
    private List<Booking> bookingList;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(bookingController).build();

        booking1 = new Booking();
        booking1.setBookingId(1L);
        booking1.setDateOfBooking(LocalDateTime.of(2024, 9, 24, 10, 0));


        booking2 = new Booking();
        booking2.setBookingId(2L);
        booking2.setDateOfBooking(LocalDateTime.of(2024, 9, 25, 12, 30));


        bookingList = new ArrayList<>();
        bookingList.add(booking1);
        bookingList.add(booking2);
    }

    @Test
    public void testCreateBooking() throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());

        when(bookingService.createBooking(any(Booking.class))).thenReturn(booking1);

        mockMvc.perform(post("/api/bookings/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(booking1)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.bookingId").value(1L))
                .andExpect(jsonPath("$.dateOfBooking").value("2024-09-24T10:00:00")); // Asegúrate de que el formato coincide

        verify(bookingService, times(1)).createBooking(any(Booking.class));
    }

    @Test
    public void testGetBookingById() throws Exception {
        when(bookingService.getBookingById(1L)).thenReturn(booking1);

        mockMvc.perform(get("/api/bookings/{id}", 1L))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.bookingId").value(1L));

        verify(bookingService, times(1)).getBookingById(1L);
    }

    @Test
    public void testGetBookingById_NotFound() throws Exception {
        when(bookingService.getBookingById(3L)).thenReturn(null);

        mockMvc.perform(get("/api/bookings/{id}", 3L))
                .andExpect(status().isNotFound());

        verify(bookingService, times(1)).getBookingById(3L);
    }

    @Test
    public void testGetAllBookings() throws Exception {
        when(bookingService.getAllBookings()).thenReturn(bookingList);

        mockMvc.perform(get("/api/bookings/"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].bookingId").value(1L))
                .andExpect(jsonPath("$[1].bookingId").value(2L));

        verify(bookingService, times(1)).getAllBookings();
    }

    @Test
    public void testUpdateBooking() throws Exception {
        when(bookingService.updateBooking(eq(1L), any(Booking.class))).thenReturn(booking1);

        mockMvc.perform(put("/api/bookings/update/{id}", 1L)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(booking1)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.bookingId").value(1L));

        verify(bookingService, times(1)).updateBooking(eq(1L), any(Booking.class));
    }

    @Test
    public void testUpdateBooking_NotFound() throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());

        when(bookingService.updateBooking(eq(3L), any(Booking.class))).thenReturn(null);

        mockMvc.perform(put("/api/bookings/update/{id}", 3L)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(booking1)))
                .andExpect(status().isNotFound());

        verify(bookingService, times(1)).updateBooking(eq(3L), any(Booking.class));
    }

    @Test
    public void testDeleteBooking() throws Exception {
        when(bookingService.deleteBooking(1L)).thenReturn(true);

        mockMvc.perform(delete("/api/bookings/delete/{id}", 1L))
                .andExpect(status().isNoContent());

        verify(bookingService, times(1)).deleteBooking(1L);
    }

    @Test
    public void testDeleteBooking_NotFound() throws Exception {
        when(bookingService.deleteBooking(4L)).thenReturn(false);

        mockMvc.perform(delete("/api/bookings/delete/{id}", 4L))
                .andExpect(status().isNotFound());

        verify(bookingService, times(1)).deleteBooking(4L);
    }
}