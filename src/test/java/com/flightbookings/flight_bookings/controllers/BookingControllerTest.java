package com.flightbookings.flight_bookings.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.flightbookings.flight_bookings.models.Booking;
import com.flightbookings.flight_bookings.models.User;
import com.flightbookings.flight_bookings.services.BookingServiceImpl;
import com.flightbookings.flight_bookings.services.UserServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static com.flightbookings.flight_bookings.models.ERole.USER;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootApplication
@EnableAutoConfiguration
public class BookingControllerTest {

    @Mock
    private BookingServiceImpl bookingService;

    @Mock
    private UserServiceImpl userService;

    @InjectMocks
    private BookingController bookingController;

    private MockMvc mockMvc;
    private ObjectMapper objectMapper; // Declarar ObjectMapper aquí
    private User user;
    private Booking booking1;
    private List<Booking> bookingList;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(bookingController).build();

        objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule()); // Registrar el módulo aquí

        Authentication auth = mock(Authentication.class);
        when(auth.getName()).thenReturn("testUser");
        SecurityContext securityContext = mock(SecurityContext.class);
        when(securityContext.getAuthentication()).thenReturn(auth);
        SecurityContextHolder.setContext(securityContext);

        user = new User();
        user.setId(1L);
        user.setRole(USER);
        user.setUsername("testUser");

        booking1 = new Booking();
        booking1.setBookingId(1L);
        booking1.setDateOfBooking(LocalDateTime.of(2024, 9, 24, 10, 0));
        booking1.setUser(user);

        bookingList = new ArrayList<>();
        bookingList.add(booking1);
    }

    @Test
    public void testCreateBooking() throws Exception {
        when(userService.getUserByUsername("testUser")).thenReturn(user);
        when(bookingService.createBooking(any(Booking.class), eq(user))).thenReturn(booking1);

        mockMvc.perform(post("/api/bookings/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(booking1)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.bookingId").value(1L))
                .andExpect(jsonPath("$.dateOfBooking").value("24-09-2024 10:00")); // Asegúrate del formato ISO

        verify(bookingService, times(1)).createBooking(any(Booking.class), eq(user));
    }


    @Test
    public void testGetBookingById() throws Exception {
        when(bookingService.getBookingById(1L)).thenReturn(booking1);

        mockMvc.perform(get("/api/bookings/{id}", 1L))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.bookingId").value(1L));

        verify(bookingService, times(1)).getBookingById(1L);
    }

    @Test
    public void testGetBookingById_NotFound() throws Exception {
        when(bookingService.getBookingById(3L)).thenReturn(null);

        mockMvc.perform(get("/api/bookings/{id}", 3L))
                .andExpect(status().isNotFound());

        verify(bookingService, times(1)).getBookingById(3L);
    }

    @Test
    public void testGetAllBookings() throws Exception {
        when(bookingService.getAllBookings()).thenReturn(bookingList);

        mockMvc.perform(get("/api/bookings/"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].bookingId").value(1L)); // Solo verificamos el primer elemento

        verify(bookingService, times(1)).getAllBookings();
    }

    @Test
    public void testGetBookingsForUser() throws Exception {
        when(userService.getUserById(1L)).thenReturn(user);
        when(bookingService.getBookingsByUser(user)).thenReturn(bookingList);

        mockMvc.perform(get("/api/bookings/user/{id}", 1L))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].bookingId").value(1L)); // Solo verificamos el primer elemento

        verify(bookingService, times(1)).getBookingsByUser(user);
    }

    @Test
    public void testUpdateBooking() throws Exception {
        when(bookingService.updateBooking(eq(1L), any(Booking.class))).thenReturn(booking1);

        mockMvc.perform(put("/api/bookings/update/{id}", 1L)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(booking1)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.bookingId").value(1L))
                .andExpect(jsonPath("$.dateOfBooking").value("24-09-2024 10:00"));
    }


    @Test
    public void testUpdateBooking_NotFound() throws Exception {
        when(bookingService.updateBooking(eq(3L), any(Booking.class))).thenReturn(null);

        mockMvc.perform(put("/api/bookings/update/{id}", 3L)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(booking1)))
                .andExpect(status().isNotFound());

        verify(bookingService, times(1)).updateBooking(eq(3L), any(Booking.class));
    }

    @Test
    public void testDeleteBooking() throws Exception {
        when(bookingService.deleteBooking(1L)).thenReturn(true);

        mockMvc.perform(delete("/api/bookings/delete/{id}", 1L))
                .andExpect(status().isNoContent());

        verify(bookingService, times(1)).deleteBooking(1L);
    }

    @Test
    public void testDeleteBooking_NotFound() throws Exception {
        when(bookingService.deleteBooking(4L)).thenReturn(false);

        mockMvc.perform(delete("/api/bookings/delete/{id}", 4L))
                .andExpect(status().isNotFound());

        verify(bookingService, times(1)).deleteBooking(4L);
    }
}
