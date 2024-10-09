package com.flightbookings.flight_bookings.controllers;


import com.flightbookings.flight_bookings.dtos.DTOFlight.FlightConverter;
import com.flightbookings.flight_bookings.dtos.DTOFlight.FlightDTO;
import com.flightbookings.flight_bookings.models.Flight;
import com.flightbookings.flight_bookings.models.EFlightAirplane;
import com.flightbookings.flight_bookings.services.interfaces.FlightService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

class FlightControllerTest {

    private MockMvc mockMvc;

    @Mock
    private FlightService flightService;

    @Mock
    private FlightConverter flightConverter;

    @InjectMocks
    private FlightController flightController;

    private Flight flight;
    private FlightDTO flightDTO;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(flightController).build();

        flight = new Flight();
        flight.setFlightNumber(101);
        flight.setNumRows(10);
        flight.setDepartureTime(LocalDateTime.now().plusDays(1)); // Debe ser en el futuro
        flight.setArrivalTime(LocalDateTime.now().plusDays(1).plusHours(2)); // Debe ser después de departureTime
        flight.setFlightPrice(BigDecimal.valueOf(200));
        flight.setFlightAirplane(EFlightAirplane.BOEING_777);

        flightDTO = new FlightDTO();
        flightDTO.setFlightNumber(101);
        flightDTO.setNumRows(10);
        flightDTO.setDepartureTime(flight.getDepartureTime());
        flightDTO.setArrivalTime(flight.getArrivalTime());
        flightDTO.setFlightPrice(flight.getFlightPrice());
        flightDTO.setFlightAirplane(EFlightAirplane.BOEING_777);
    }

    @Test
    void testCreateFlight() throws Exception {
        when(flightConverter.dtoToFlight(any(FlightDTO.class))).thenReturn(flight);
        when(flightService.createFlight(any(Flight.class))).thenReturn(flight);
        when(flightConverter.flightToDto(any(Flight.class))).thenReturn(flightDTO);

        mockMvc.perform(post("/api/v1/flight/create")
                        .contentType("application/json")
                        .content("{\"flightNumber\":101,\"numRows\":10,\"departureTime\":\"01-01-2024 12:00:00\",\"arrivalTime\":\"01-01-2024 14:00:00\",\"flightPrice\":200,\"flightAirplane\":\"BOEING_777\"}"))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.flightNumber").value(101));
    }

    @Test
    void testGetFlightByIdFound() throws Exception {
        when(flightService.getFlightById(1L)).thenReturn(flight);
        when(flightConverter.flightToDto(flight)).thenReturn(flightDTO);

        mockMvc.perform(get("/api/v1/flight/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.flightNumber").value(101))
                .andExpect(jsonPath("$.numRows").value(10));
    }

    @Test
    void testGetFlightByIdNotFound() throws Exception {
        when(flightService.getFlightById(1L)).thenReturn(null);

        mockMvc.perform(get("/api/v1/flight/1"))
                .andExpect(status().isNotFound());
    }

    @Test
    void testDeleteFlightFound() throws Exception {
        when(flightService.deleteFlight(1L)).thenReturn(true);

        mockMvc.perform(delete("/api/v1/flight/delete/1"))
                .andExpect(status().isNoContent());
    }

    @Test
    void testDeleteFlightNotFound() throws Exception {
        when(flightService.deleteFlight(1L)).thenReturn(false);

        mockMvc.perform(delete("/api/v1/flight/delete/1"))
                .andExpect(status().isNotFound());
    }

    @Test
    void testDelayFlight() throws Exception {
        mockMvc.perform(post("/api/v1/flight/1/delay?newDepartureTime=2024-01-01T15:00:00"))
                .andExpect(status().isAccepted())
                .andExpect(content().string("Flight delayed successfully."));
    }
}
