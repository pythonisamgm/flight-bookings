package com.flightbookings.flight_bookings.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.flightbookings.flight_bookings.models.EFlightAirplane;
import com.flightbookings.flight_bookings.models.Flight;
import com.flightbookings.flight_bookings.services.FlightServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class FlightControllerTest {

    @Mock
    private FlightServiceImpl flightService;

    @InjectMocks
    private FlightController flightController;

    private MockMvc mockMvc;
    private Flight flight1;
    private Flight flight2;
    private List<Flight> flightList;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(flightController).build();

        flight1 = new Flight();
        flight1.setId(1L);
        flight1.setFlightNumber(123);
        flight1.setDepartureTime(LocalDateTime.of(2024, 9, 30, 14, 0));
        flight1.setArrivalTime(LocalDateTime.of(2024, 9, 30, 17, 30));
        flight1.setFlightAirplane(EFlightAirplane.Boeing_737);
        flight1.setCapacityPlane(180);
        flight1.setAvailability(true);
        flight1.setNumRows(30);
        flight1.setFlightPrice(new BigDecimal("300.00"));

        flight2 = new Flight();
        flight2.setId(2L);
        flight2.setFlightNumber(456);
        flight2.setDepartureTime(LocalDateTime.of(2024, 10, 1, 9, 0));
        flight2.setArrivalTime(LocalDateTime.of(2024, 10, 1, 12, 0));
        flight2.setFlightAirplane(EFlightAirplane.Airbus_A321);
        flight2.setCapacityPlane(160);
        flight2.setAvailability(true);
        flight2.setNumRows(28);
        flight2.setFlightPrice(new BigDecimal("200.00"));

        flightList = new ArrayList<>();
        flightList.add(flight1);
        flightList.add(flight2);
    }

    @Test
    public void testCreateFlight() throws Exception {
        when(flightService.createFlight(any(Flight.class))).thenReturn(flight1);

        mockMvc.perform(post("/api/v1/flight/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(flight1)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.flightNumber").value(123))
                .andExpect(jsonPath("$.departureTime").value("30-09-2024 14:00:00"))
                .andExpect(jsonPath("$.arrivalTime").value("30-09-2024 17:30:00"))
                .andExpect(jsonPath("$.flightAirplane").value("BOEING_737"))
                .andExpect(jsonPath("$.capacityPlane").value(180))
                .andExpect(jsonPath("$.availability").value(true))
                .andExpect(jsonPath("$.numRows").value(30))
                .andExpect(jsonPath("$.flightPrice").value(300.00));

        verify(flightService, times(1)).createFlight(any(Flight.class));
    }

    @Test
    public void testGetFlightById() throws Exception {
        when(flightService.getFlightById(1L)).thenReturn(flight1);

        mockMvc.perform(get("/api/v1/flight/{id}", 1L))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.flightNumber").value(123))
                .andExpect(jsonPath("$.departureTime").value("30-09-2024 14:00:00"))
                .andExpect(jsonPath("$.arrivalTime").value("30-09-2024 17:30:00"))
                .andExpect(jsonPath("$.flightAirplane").value("Boeing_737"))
                .andExpect(jsonPath("$.capacityPlane").value(180))
                .andExpect(jsonPath("$.availability").value(true))
                .andExpect(jsonPath("$.numRows").value(30))
                .andExpect(jsonPath("$.flightPrice").value(300.00));

        verify(flightService, times(1)).getFlightById(1L);
    }

    @Test
    public void testGetAllFlights() throws Exception {
        when(flightService.getAllFlights()).thenReturn(flightList);

        mockMvc.perform(get("/api/v1/flight/"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1L))
                .andExpect(jsonPath("$[1].id").value(2L))
                .andExpect(jsonPath("$[0].flightNumber").value(123))
                .andExpect(jsonPath("$[1].flightNumber").value(456))
                .andExpect(jsonPath("$[0].departureTime").value("30-09-2024 14:00:00"))
                .andExpect(jsonPath("$[1].departureTime").value("01-10-2024 09:00:00"))
                .andExpect(jsonPath("$[0].arrivalTime").value("30-09-2024 17:30:00"))
                .andExpect(jsonPath("$[1].arrivalTime").value("01-10-2024 12:00:00"))
                .andExpect(jsonPath("$[0].flightPrice").value(300.00))
                .andExpect(jsonPath("$[1].flightPrice").value(200.00));

        verify(flightService, times(1)).getAllFlights();
    }

    @Test
    public void testUpdateFlight() throws Exception {
        when(flightService.updateFlight(eq(1L), any(Flight.class))).thenReturn(flight1);

        mockMvc.perform(put("/api/v1/flight/update/{id}", 1L)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(flight1)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.flightNumber").value(123))
                .andExpect(jsonPath("$.departureTime").value("30-09-2024 14:00:00"))
                .andExpect(jsonPath("$.arrivalTime").value("30-09-2024 17:30:00"))
                .andExpect(jsonPath("$.flightAirplane").value("BOEING_737"))
                .andExpect(jsonPath("$.capacityPlane").value(180))
                .andExpect(jsonPath("$.availability").value(true))
                .andExpect(jsonPath("$.numRows").value(30))
                .andExpect(jsonPath("$.flightPrice").value(300.00));

        verify(flightService, times(1)).updateFlight(eq(1L), any(Flight.class));
    }

    @Test
    public void testDeleteFlight() throws Exception {
        when(flightService.deleteFlight(1L)).thenReturn(true);

        mockMvc.perform(delete("/api/v1/flight/delete/{id}", 1L))
                .andExpect(status().isNoContent());

        verify(flightService, times(1)).deleteFlight(1L);
    }
}
