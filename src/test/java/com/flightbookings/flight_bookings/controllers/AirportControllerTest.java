package com.flightbookings.flight_bookings.controllers;

import com.flightbookings.flight_bookings.dtos.DTOAirport.AirportConverter;
import com.flightbookings.flight_bookings.dtos.DTOAirport.AirportDTO;
import com.flightbookings.flight_bookings.models.Airport;
import com.flightbookings.flight_bookings.models.ECountry;
import com.flightbookings.flight_bookings.services.interfaces.AirportService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


class AirportControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Mock
    private AirportService airportService;

    @Mock
    private AirportConverter airportConverter;

    @InjectMocks
    private AirportController airportController;

    private Airport airport1;
    private Airport airport2;
    private AirportDTO airportDTO1;
    private AirportDTO airportDTO2;
    private List<Airport> airportList;
    private List<AirportDTO> airportDTOList;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(airportController).build();

        airport1 = new Airport("MAD", "Madrid-Barajas", "Madrid", ECountry.ESPAÑA);
        airport2 = new Airport("BCN", "Barcelona-El Prat", "Barcelona", ECountry.ESPAÑA);

        airportDTO1 = new AirportDTO();
        airportDTO1.setAirportCode("MAD");
        airportDTO1.setAirportName("Madrid-Barajas");
        airportDTO1.setAirportCity("Madrid");
        airportDTO1.setAirportCountry("ESPAÑA");

        airportDTO2 = new AirportDTO();
        airportDTO2.setAirportCode("BCN");
        airportDTO2.setAirportName("Barcelona-El Prat");
        airportDTO2.setAirportCity("Barcelona");
        airportDTO2.setAirportCountry("ESPAÑA");

        airportList = new ArrayList<>();
        airportList.add(airport1);
        airportList.add(airport2);

        airportDTOList = new ArrayList<>();
        airportDTOList.add(airportDTO1);
        airportDTOList.add(airportDTO2);
    }

    @Test
    void testGetAllAirports() throws Exception {
        when(airportService.getAllAirports()).thenReturn(airportList);
        when(airportConverter.airportsToDtoList(airportList)).thenReturn(airportDTOList);

        mockMvc.perform(get("/api/airports"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.length()").value(2));
    }

    @Test
    void testCreateAirport() throws Exception {
        String airportJson = "{\"airportCode\":\"MAD\",\"airportName\":\"Madrid-Barajas\",\"airportCity\":\"Madrid\",\"airportCountry\":\"ESPAÑA\"}";

        when(airportConverter.dtoToAirport(any(AirportDTO.class))).thenReturn(airport1);
        when(airportService.createAirport(any(Airport.class))).thenReturn(airport1);
        when(airportConverter.airportToDto(airport1)).thenReturn(airportDTO1);

        mockMvc.perform(post("/api/airports")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(airportJson))
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.airportCode").value("MAD"))
                .andExpect(jsonPath("$.airportName").value("Madrid-Barajas"));
    }
}
