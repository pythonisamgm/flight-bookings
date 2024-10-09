package com.flightbookings.flight_bookings.controllers;

import com.flightbookings.flight_bookings.dtos.DTOBooking.BookingConverter;
import com.flightbookings.flight_bookings.dtos.DTOBooking.BookingDTO;
import com.flightbookings.flight_bookings.models.Booking;
import com.flightbookings.flight_bookings.models.User;
import com.flightbookings.flight_bookings.services.interfaces.BookingService;
import com.flightbookings.flight_bookings.services.interfaces.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.security.core.Authentication;

import java.security.Principal;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

class BookingControllerTest {

    @Mock
    private BookingService bookingService;

    @Mock
    private UserService userService;

    @Mock
    private BookingConverter bookingConverter;

    @InjectMocks
    private BookingController bookingController;

    private MockMvc mockMvc;

    private BookingDTO bookingDTO;
    private Booking booking;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(bookingController).build();

        bookingDTO = new BookingDTO();
        bookingDTO.setBookingId(1L);
        bookingDTO.setFlightId(100L);
        bookingDTO.setPassengerId(200L);
        bookingDTO.setSeatName("A1");

        booking = new Booking();
        booking.setBookingId(1L);
    }

    @Test
    void testCreateBooking() throws Exception {
        when(bookingService.createBooking(anyLong(), anyLong(), anyString(), anyLong())).thenReturn(booking);
        when(bookingConverter.bookingToDto(any(Booking.class))).thenReturn(bookingDTO);

        mockMvc.perform(post("/api/v1/bookings/create/100/200/A1/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.bookingId").value(1L))
                .andExpect(jsonPath("$.flightId").value(100L))
                .andExpect(jsonPath("$.passengerId").value(200L))
                .andExpect(jsonPath("$.seatName").value("A1"));

        verify(bookingService, times(1)).createBooking(anyLong(), anyLong(), anyString(), anyLong());
    }

    @Test
    void testGetBookingById() throws Exception {
        Principal principal = mock(Principal.class);
        when(principal.getName()).thenReturn("username");  // Simula un nombre de usuario

        // Configura el servicio para encontrar el usuario
        User user = new User();
        user.setUsername("username");
        when(userService.findByUsername("username")).thenReturn(user);
        when(bookingService.getBookingById(anyLong(), eq(user))).thenReturn(booking);
        when(bookingConverter.bookingToDto(any(Booking.class))).thenReturn(bookingDTO);

        mockMvc.perform(get("/api/v1/bookings/1")
                        .principal(principal)  // Pasa el Principal mockeado
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.bookingId").value(1L))
                .andExpect(jsonPath("$.flightId").value(100L))
                .andExpect(jsonPath("$.passengerId").value(200L))
                .andExpect(jsonPath("$.seatName").value("A1"));
    }
    @Test
    void testGetAllBookings() throws Exception {
        when(bookingService.getAllBookings()).thenReturn(List.of(booking));
        when(bookingConverter.bookingToDto(any(Booking.class))).thenReturn(bookingDTO);

        mockMvc.perform(get("/api/v1/bookings/all")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].bookingId").value(1L))
                .andExpect(jsonPath("$[0].flightId").value(100L))
                .andExpect(jsonPath("$[0].passengerId").value(200L))
                .andExpect(jsonPath("$[0].seatName").value("A1"));

        verify(bookingService, times(1)).getAllBookings();
    }

    @Test
    void testUpdateBooking() throws Exception {
        when(bookingService.updateBooking(any(Booking.class))).thenReturn(booking);
        when(bookingConverter.dtoToBooking(any(BookingDTO.class))).thenReturn(booking);
        when(bookingConverter.bookingToDto(any(Booking.class))).thenReturn(bookingDTO);

        mockMvc.perform(put("/api/v1/bookings/update/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{ \"flightId\": 100, \"passengerId\": 200, \"seatName\": \"A1\" }"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.bookingId").value(1L))
                .andExpect(jsonPath("$.flightId").value(100L))
                .andExpect(jsonPath("$.passengerId").value(200L))
                .andExpect(jsonPath("$.seatName").value("A1"));

        verify(bookingService, times(1)).updateBooking(any(Booking.class));
    }

    @Test
    void testDeleteBookingById() throws Exception {
        when(bookingService.deleteBooking(anyLong())).thenReturn(true);

        mockMvc.perform(delete("/api/v1/bookings/delete/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());

        verify(bookingService, times(1)).deleteBooking(anyLong());
    }
}
