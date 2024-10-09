package com.flightbookings.flight_bookings.controllers;

import com.flightbookings.flight_bookings.services.interfaces.SeatService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class SeatControllerTest {

    @Mock
    private SeatService seatService;

    @InjectMocks
    private SeatController seatController;

    private MockMvc mockMvc;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(seatController).build();
    }

    @Test
    public void testInitializeSeats_Success() throws Exception {
        doNothing().when(seatService).initializeSeatsForAllFlights();

        mockMvc.perform(post("/api/v1/seat/initialize")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string("Seats initialized for all flights."));

        verify(seatService, times(1)).initializeSeatsForAllFlights();
    }

    @Test
    public void testInitializeSeats_Failure() throws Exception {
        doThrow(new RuntimeException("Error initializing seats")).when(seatService).initializeSeatsForAllFlights();

        mockMvc.perform(post("/api/v1/seat/initialize")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isInternalServerError())
                .andExpect(content().string("Error initializing seats: Error initializing seats"));

        verify(seatService, times(1)).initializeSeatsForAllFlights();
    }
}
