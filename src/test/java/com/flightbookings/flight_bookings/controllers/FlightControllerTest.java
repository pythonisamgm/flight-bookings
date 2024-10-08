package com.flightbookings.flight_bookings.controllers;

import com.flightbookings.flight_bookings.dtos.DTOFlight.FlightDTO;
import com.flightbookings.flight_bookings.models.EFlightAirplane;
import com.flightbookings.flight_bookings.models.Flight;
import com.flightbookings.flight_bookings.repositories.IFlightRepository;
import com.flightbookings.flight_bookings.services.interfaces.FlightService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
class FlightControllerTest {

    @InjectMocks
    private MockMvc mockMvc;
    private FlightService flightService;

    @Mock
    private IFlightRepository flightRepository;
    private Flight flight;


    @BeforeEach
    void setUp() {
        flightRepository.deleteAll();

        flight = new Flight();
        flight.setFlightId(1L);
        flight.setFlightNumber(123);
        flight.setNumRows(10);
        flight.setDepartureTime(LocalDateTime.now());
        flight.setArrivalTime(LocalDateTime.now().plusHours(2));
        flight.setFlightAirplane(EFlightAirplane.AIRBUS_A320);
        flight.setCapacityPlane(180);
        flight.setFlightPrice(BigDecimal.valueOf(200.00));
        flight.setAvailability(true);

        flightRepository.save(flight);
    }

    @Test
    void testCreateFlight() throws Exception {
        FlightDTO flightDTO = new FlightDTO();
        flightDTO.setFlightNumber(456);
        flightDTO.setNumRows(15);
        flightDTO.setDepartureTime(LocalDateTime.now());
        flightDTO.setArrivalTime(LocalDateTime.now().plusHours(3));
        flightDTO.setFlightAirplane(EFlightAirplane.AIRBUS_A320);
        flightDTO.setCapacityPlane(200);
        flightDTO.setFlightPrice(BigDecimal.valueOf(250.00));
        flightDTO.setAvailability(true);

        mockMvc.perform(post("/flights")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"flightNumber\":456,\"numRows\":15,\"departureTime\":\"" +
                                flightDTO.getDepartureTime() + "\",\"arrivalTime\":\"" +
                                flightDTO.getArrivalTime() + "\",\"flightAirplane\":\"" +
                                flightDTO.getFlightAirplane() + "\",\"capacityPlane\":" +
                                flightDTO.getCapacityPlane() + ",\"flightPrice\":" +
                                flightDTO.getFlightPrice() + ",\"availability\":" +
                                flightDTO.isAvailability() + "}"))
                .andExpect(status().isCreated());
    }

    @Test
    void testGetFlightById_Found() throws Exception {
        mockMvc.perform(get("/flights/1"))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.flightId").value(1L));
    }

    @Test
    void testGetFlightById_NotFound() throws Exception {
        mockMvc.perform(get("/flights/999"))
                .andExpect(status().isNotFound());
    }

    @Test
    void testGetAllFlights() throws Exception {
        mockMvc.perform(get("/flights"))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].flightId").value(1L));
    }

    @Test
    void testUpdateFlight_Found() throws Exception {
        mockMvc.perform(put("/flights/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"flightId\":1,\"flightNumber\":123,\"numRows\":10,\"departureTime\":\"" +
                                flight.getDepartureTime() + "\",\"arrivalTime\":\"" +
                                flight.getArrivalTime() + "\",\"flightAirplane\":\"" +
                                flight.getFlightAirplane() + "\",\"capacityPlane\":" +
                                flight.getCapacityPlane() + ",\"flightPrice\":" +
                                flight.getFlightPrice() + ",\"availability\":" +
                                flight.isAvailability() + "}"))
                .andExpect(status().isOk());
    }

    @Test
    void testUpdateFlight_NotFound() throws Exception {
        mockMvc.perform(put("/flights/999")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"flightId\":999,\"flightNumber\":999,\"numRows\":0,\"departureTime\":\"" +
                                LocalDateTime.now() + "\",\"arrivalTime\":\"" +
                                LocalDateTime.now().plusHours(1) + "\",\"flightAirplane\":\"" +
                                EFlightAirplane.AIRBUS_A320 + "\",\"capacityPlane\":0,\"flightPrice\":" +
                                BigDecimal.ZERO + ",\"availability\":false}"))
                .andExpect(status().isNotFound());
    }

    @Test
    void testDeleteFlight_Found() throws Exception {
        mockMvc.perform(delete("/flights/1"))
                .andExpect(status().isNoContent());
    }

    @Test
    void testDeleteFlight_NotFound() throws Exception {
        mockMvc.perform(delete("/flights/999"))
                .andExpect(status().isNotFound());
    }

    @Test
    void testCancelFlight() throws Exception {
        mockMvc.perform(delete("/flights/1/cancel"))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.content().string("Flight canceled successfully."));
    }

    @Test
    void testDelayFlight() throws Exception {
        String newDepartureTime = LocalDateTime.now().plusHours(1).toString();

        mockMvc.perform(put("/flights/1/delay")
                        .param("newDepartureTime", newDepartureTime))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.content().string("Flight delayed successfully."));
    }

    @Test
    void testUpdateAvailability() throws Exception {
        mockMvc.perform(put("/flights/update-availability"))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.content().string("Flight availability updated successfully."));
    }

    @Test
    void testGetFlightsByAirplaneType() throws Exception {
        mockMvc.perform(get("/flights/airplane-type/AIRBUS_A320"))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].flightId").value(1L));
    }
}
