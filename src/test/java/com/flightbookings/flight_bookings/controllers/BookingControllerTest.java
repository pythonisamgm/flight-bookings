package com.flightbookings.flight_bookings.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.flightbookings.flight_bookings.models.Booking;
import com.flightbookings.flight_bookings.models.Flight;
import com.flightbookings.flight_bookings.models.Passenger;
import com.flightbookings.flight_bookings.models.Seat;
import com.flightbookings.flight_bookings.services.interfaces.BookingService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class BookingControllerTest {

    @Mock
    private BookingService bookingService;

    @InjectMocks
    private BookingController bookingController;

    private MockMvc mockMvc;

    private ObjectMapper objectMapper;

    private Booking booking1;
    private Booking booking2;
    private Passenger passenger1;
    private Flight flight1;
    private Seat seat1;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(bookingController).build();

        objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());

        passenger1 = new Passenger(); // Configure this object as needed
        flight1 = new Flight(); // Configure this object as needed
        seat1 = new Seat(); // Configure this object as needed

        booking1 = new Booking(1L, LocalDateTime.of(2024, 9, 24, 10, 0), passenger1, flight1, seat1);
        booking2 = new Booking(2L, LocalDateTime.of(2024, 9, 25, 12, 30), passenger1, flight1, seat1);
    }

    @Test
    void createBooking() throws Exception {
        when(bookingService.createBooking(anyLong(), anyLong(), anyString())).thenReturn(booking1);

        String bookingJson = "{"
                + "\"flightId\": 1,"
                + "\"passengerId\": 1,"
                + "\"seatName\": \"1A\""
                + "}";

        mockMvc.perform(post("/api/v1/booking")
                        .param("flightId", "1")
                        .param("passengerId", "1")
                        .param("seatName", "1A")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(bookingJson))
                .andExpect(status().isCreated())
                .andExpect(content().json(objectMapper.writeValueAsString(booking1)));
    }



    @Test
    void updateBooking() throws Exception {
        when(bookingService.updateBooking(any(Booking.class))).thenReturn(booking1);

        String updatedBookingJson = objectMapper.writeValueAsString(booking1);

        mockMvc.perform(put("/api/v1/booking/{id}", 1L)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(updatedBookingJson))
                .andExpect(status().isOk())
                .andExpect(content().json(updatedBookingJson));
    }


}
