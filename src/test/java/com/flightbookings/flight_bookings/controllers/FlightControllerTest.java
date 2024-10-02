package com.flightbookings.flight_bookings.controllers;

import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.flightbookings.flight_bookings.models.Flight;
import com.flightbookings.flight_bookings.models.EFlightAirplane;
import com.flightbookings.flight_bookings.services.interfaces.FlightService;
import com.fasterxml.jackson.databind.ObjectMapper;
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
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

class FlightControllerTest {

    @InjectMocks
    private FlightController flightController;
    private ObjectMapper objectMapper;
    private MockMvc mockMvc;
    private Flight flight1;
    private Flight flight2;

    private final DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ISO_LOCAL_DATE_TIME;

    @Mock
    private FlightService flightService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(flightController).build();

        objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());

        flight1 = new Flight();
        flight1.setFlightId(1L);
        flight1.setFlightNumber(101);
        flight1.setDepartureTime(LocalDateTime.of(2024, 10, 19, 20, 10, 20));
        flight1.setArrivalTime(LocalDateTime.of(2024, 10, 19, 21, 10, 20));
        flight1.setFlightAirplane(EFlightAirplane.Boeing_747);
        flight1.setCapacityPlane(200);
        flight1.setAvailability(true);
        flight1.setFlightPrice(BigDecimal.valueOf(150.00));

        flight2 = new Flight();
        flight2.setFlightId(2L);
        flight2.setFlightNumber(102);
        flight2.setDepartureTime(LocalDateTime.of(2024, 10, 19, 20, 10, 20));
        flight2.setArrivalTime(LocalDateTime.of(2024, 10, 19, 21, 10, 20));
        flight2.setFlightAirplane(EFlightAirplane.Boeing_777);
        flight2.setCapacityPlane(250);
        flight2.setAvailability(true);
        flight2.setFlightPrice(BigDecimal.valueOf(175.00));
    }

    @Test
    void test_Create_Flight() throws Exception {
        when(flightService.createFlight(any(Flight.class))).thenReturn(flight1);

        mockMvc.perform(post("/api/v1/flight/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(flight1)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.flightId").value(flight1.getFlightId()))
                .andExpect(jsonPath("$.flightNumber").value(flight1.getFlightNumber()))
                .andExpect(jsonPath("$.departureTime").value(flight1.getDepartureTime().format(dateTimeFormatter)))
                .andExpect(jsonPath("$.arrivalTime").value(flight1.getArrivalTime().format(dateTimeFormatter)))
                .andExpect(jsonPath("$.flightAirplane").value(flight1.getFlightAirplane().toString()))
                .andExpect(jsonPath("$.capacityPlane").value(flight1.getCapacityPlane()))
                .andExpect(jsonPath("$.availability").value(flight1.isAvailability()))
                .andExpect(jsonPath("$.flightPrice").value(flight1.getFlightPrice().doubleValue()));
    }

    @Test
    void test_Get_Flight_By_Id() throws Exception {
        when(flightService.getFlightById(1L)).thenReturn(flight1);

        mockMvc.perform(get("/api/v1/flight/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.flightNumber").value(flight1.getFlightNumber()))
                .andExpect(jsonPath("$.departureTime").value(flight1.getDepartureTime().format(dateTimeFormatter)))
                .andExpect(jsonPath("$.arrivalTime").value(flight1.getArrivalTime().format(dateTimeFormatter)))
                .andExpect(jsonPath("$.flightAirplane").value(flight1.getFlightAirplane().toString()))
                .andExpect(jsonPath("$.capacityPlane").value(flight1.getCapacityPlane()))
                .andExpect(jsonPath("$.availability").value(flight1.isAvailability()))
                .andExpect(jsonPath("$.flightPrice").value(flight1.getFlightPrice().doubleValue()));
    }

    @Test
    void test_Get_All_Flights() throws Exception {
        List<Flight> flightList = new ArrayList<>();
        flightList.add(flight1);
        when(flightService.getAllFlights()).thenReturn(flightList);

        mockMvc.perform(get("/api/v1/flight/")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$[0].flightNumber").value(flight1.getFlightNumber()))
                .andExpect(jsonPath("$[0].departureTime").value(flight1.getDepartureTime().format(dateTimeFormatter)))
                .andExpect(jsonPath("$[0].arrivalTime").value(flight1.getArrivalTime().format(dateTimeFormatter)))
                .andExpect(jsonPath("$[0].flightAirplane").value(flight1.getFlightAirplane().toString()))
                .andExpect(jsonPath("$[0].capacityPlane").value(flight1.getCapacityPlane()))
                .andExpect(jsonPath("$[0].availability").value(flight1.isAvailability()))
                .andExpect(jsonPath("$[0].flightPrice").value(flight1.getFlightPrice().doubleValue()));
    }

    @Test
    void test_Update_Flight() throws Exception {
        when(flightService.updateFlight(eq(1L), any(Flight.class))).thenReturn(flight2);

        mockMvc.perform(put("/api/v1/flight/update/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(flight2)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.flightNumber").value(flight2.getFlightNumber()))
                .andExpect(jsonPath("$.departureTime").value(flight2.getDepartureTime().format(dateTimeFormatter)))
                .andExpect(jsonPath("$.arrivalTime").value(flight2.getArrivalTime().format(dateTimeFormatter)))
                .andExpect(jsonPath("$.flightAirplane").value(flight2.getFlightAirplane().toString()))
                .andExpect(jsonPath("$.capacityPlane").value(flight2.getCapacityPlane()))
                .andExpect(jsonPath("$.availability").value(flight2.isAvailability()))
                .andExpect(jsonPath("$.flightPrice").value(flight2.getFlightPrice().doubleValue()));
    }

    @Test
    void test_Delete_Flight() throws Exception {
        when(flightService.deleteFlight(1L)).thenReturn(true);

        mockMvc.perform(delete("/api/v1/flight/delete/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());
    }

    @Test
    void test_Cancel_Flight() throws Exception {
        doNothing().when(flightService).cancelFlight(1L);

        mockMvc.perform(delete("/api/v1/flight/1/cancel")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string("Flight canceled successfully."));
    }

    @Test
    void test_Delay_Flight() throws Exception {
        String newDepartureTime = LocalDateTime.now().plusHours(4).toString();
        doNothing().when(flightService).delayFlight(1L, LocalDateTime.parse(newDepartureTime));

        mockMvc.perform(post("/api/v1/flight/1/delay")
                        .param("newDepartureTime", newDepartureTime)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string("Flight delayed successfully."));
    }

    @Test
    void test_Update_Availability() throws Exception {
        doNothing().when(flightService).updateFlightAvailability();

        mockMvc.perform(post("/api/v1/flight/updateAvailability")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string("Flight availability updated successfully."));
    }

    @Test
    void test_Get_Flights_By_Airplane_Type() throws Exception {
        List<Flight> flightList = new ArrayList<>();
        flightList.add(flight1);
        when(flightService.getFlightsByAirplaneType(EFlightAirplane.Boeing_747)).thenReturn(flightList);

        mockMvc.perform(get("/api/v1/flight/byAirplaneType")
                        .param("airplaneType", EFlightAirplane.Boeing_747.toString())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$[0].flightNumber").value(flight1.getFlightNumber()));
    }
}
