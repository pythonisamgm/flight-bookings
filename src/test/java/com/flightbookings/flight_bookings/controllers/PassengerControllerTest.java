package com.flightbookings.flight_bookings.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.flightbookings.flight_bookings.dtos.DTOPassenger.PassengerDTO;
import com.flightbookings.flight_bookings.models.Passenger;
import com.flightbookings.flight_bookings.services.interfaces.PassengerService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Arrays;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

class PassengerControllerTest {

    @InjectMocks
    private PassengerController passengerController;

    @Mock
    private PassengerService passengerService;

    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(passengerController).build();
    }

    @Test
    void createPassenger() throws Exception {
        PassengerDTO passengerDTO = new PassengerDTO();
        passengerDTO.setPassengerName("John Doe");
        passengerDTO.setIdentityDoc("123456789");
        passengerDTO.setTelephone(1234567890L);

        Passenger passenger = new Passenger();

        when(passengerService.createPassenger(any(Passenger.class))).thenReturn(passenger);

        mockMvc.perform(post("/api/v1/passengers/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(passengerDTO)))
                .andExpect(status().isCreated());
    }

    @Test
    void getPassengerById() throws Exception {
        Long id = 1L;
        Passenger passenger = new Passenger();
        passenger.setPassengerName("John Doe");

        when(passengerService.getPassengerById(id)).thenReturn(passenger);

        mockMvc.perform(get("/api/v1/passengers/{id}", id))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.passengerName").value("John Doe"));
    }

    @Test
    void getPassengerById_NotFound() throws Exception {
        Long id = 1L;

        when(passengerService.getPassengerById(id)).thenReturn(null);

        mockMvc.perform(get("/api/v1/passengers/{id}", id))
                .andExpect(status().isNotFound());
    }

    @Test
    void getAllPassengers() throws Exception {
        Passenger passenger1 = new Passenger();
        passenger1.setPassengerName("John Doe");
        Passenger passenger2 = new Passenger();
        passenger2.setPassengerName("Jane Doe");

        List<Passenger> passengerList = Arrays.asList(passenger1, passenger2);

        when(passengerService.getAllPassengers()).thenReturn(passengerList);

        mockMvc.perform(get("/api/v1/passengers"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)));
    }

    @Test
    void updatePassenger() throws Exception {
        Long id = 1L;
        PassengerDTO passengerDTO = new PassengerDTO();
        passengerDTO.setPassengerName("John Doe Updated");

        Passenger updatedPassenger = new Passenger();
        when(passengerService.updatePassenger(eq(id), any(Passenger.class))).thenReturn(updatedPassenger);

        mockMvc.perform(put("/api/v1/passengers/update/{id}", id)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(passengerDTO)))
                .andExpect(status().isOk());
    }

    @Test
    void updatePassenger_NotFound() throws Exception {
        Long id = 1L;
        PassengerDTO passengerDTO = new PassengerDTO();

        when(passengerService.updatePassenger(eq(id), any(Passenger.class))).thenReturn(null);

        mockMvc.perform(put("/api/v1/passengers/update/{id}", id)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(passengerDTO)))
                .andExpect(status().isNotFound());
    }

    @Test
    void deletePassenger() throws Exception {
        Long id = 1L;

        when(passengerService.deletePassenger(id)).thenReturn(true);

        mockMvc.perform(delete("/api/v1/passengers/delete/{id}", id))
                .andExpect(status().isNoContent());
    }

    @Test
    void deletePassenger_NotFound() throws Exception {
        Long id = 1L;

        when(passengerService.deletePassenger(id)).thenReturn(false);

        mockMvc.perform(delete("/api/v1/passengers/delete/{id}", id))
                .andExpect(status().isNotFound());
    }
}
