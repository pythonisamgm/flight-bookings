package com.flightbookings.flight_bookings.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.flightbookings.flight_bookings.models.Passenger;
import com.flightbookings.flight_bookings.services.PassengerService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class PassengerControllerTest {

    @Mock
    private PassengerService passengerService;
    private MockMvc mockMvc;

    @InjectMocks
    private PassengerController passengerController;

    private Passenger passenger1;
    private Passenger passenger2;
    private List<Passenger> passengerList;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(passengerController).build();

        passenger1 = new Passenger();
        passenger1.setPassengerId(1L);
        passenger1.setPassengerName("Juan Antonio");
        passenger1.setIdentityDoc("1337");
        passenger1.setTelephone(661777777);
        passenger1.setNationality("Irlandés");

        passenger2 = new Passenger();
        passenger2.setPassengerId(2L);
        passenger2.setPassengerName("Miguel Angel");
        passenger2.setIdentityDoc("7823");
        passenger2.setTelephone(661888888);
        passenger2.setNationality("Alemania");

        passengerList = new ArrayList<>();
        passengerList.add(passenger1);
        passengerList.add(passenger2);

    }

    @Test
    public void testCreatePassenger() throws Exception {
        when(passengerService.createPassenger(any(Passenger.class))).thenReturn(passenger1);

        mockMvc.perform(post("/api/passengers/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(passenger1)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.passengerId").value(1L))
        .andExpect(jsonPath("$.passengerName").value("Juan Antonio"))
        .andExpect(jsonPath("$.identityDoc").value("7823"))
        .andExpect(jsonPath("$.telephone").value(661888888))
        .andExpect(jsonPath("$.nationality").value("Alemania"));

        verify(passengerService, times(1)).createPassenger(any(Passenger.class));
    }

    @Test
    public void testGetPassengerById() throws Exception {
        when(passengerService.getPassengerById(1L)).thenReturn(passenger1);

        mockMvc.perform(get("/api/passengers/{id}", 1L))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.passengerId").value(1L));

        verify(passengerService, times(1)).getPassengerById(1L);
    }

    @Test
    public void testGetPassengerById_NotFound() throws Exception {
        when(passengerService.getPassengerById(3L)).thenReturn(null);

        mockMvc.perform(get("/api/passengers/{id}", 3L))
                .andExpect(status().isNotFound());

        verify(passengerService, times(1)).getPassengerById(3L);
    }

    @Test
    public void testGetAllPassengers() throws Exception {
        when(passengerService.getAllPassengers()).thenReturn(passengerList);

        mockMvc.perform(get("/api/passengers"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].passengerId").value(1L))
                .andExpect(jsonPath("$[1].passengerId").value(2L));

        verify(passengerService, times(1)).getAllPassengers();
    }

    @Test
    public void testUpdatePassenger() throws Exception {
        when(passengerService.updatePassenger(eq(1L), any(Passenger.class))).thenReturn(passenger1);

        mockMvc.perform(put("/api/passengers/{id}", 1L)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(passenger1)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.passengerId").value(1L));

        verify(passengerService, times(1)).updatePassenger(eq(1L), any(Passenger.class));
    }

    @Test
    public void testUpdatePassenger_NotFound() throws Exception {
        when(passengerService.updatePassenger(eq(3L), any(Passenger.class))).thenReturn(null);

        mockMvc.perform(put("/api/passengers/{id}", 3L)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(passenger1)))
                .andExpect(status().isNotFound());

        verify(passengerService, times(1)).updatePassenger(eq(3L), any(Passenger.class));
    }

    @Test
    public void testDeletePassenger() throws Exception {
        when(passengerService.deletePassenger(1L)).thenReturn(true);

        mockMvc.perform(delete("/api/passengers/delete/{id}", 1L))
                .andExpect(status().isNoContent());

        verify(passengerService, times(1)).deletePassenger(1L);
    }

    @Test
    public void testDeletePassenger_NotFound() throws Exception {
        when(passengerService.deletePassenger(4L)).thenReturn(false);

        mockMvc.perform(delete("/api/passengers/delete/{id}", 4L))
                .andExpect(status().isNotFound());

        verify(passengerService, times(1)).deletePassenger(4L);
    }
}
