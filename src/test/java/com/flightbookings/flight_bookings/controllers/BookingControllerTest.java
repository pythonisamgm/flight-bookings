package com.flightbookings.flight_bookings.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.flightbookings.flight_bookings.models.*;
import com.flightbookings.flight_bookings.services.interfaces.BookingService;
import com.flightbookings.flight_bookings.services.interfaces.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.security.Principal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
/**
 * Test class for {@link BookingController}.
 * Verifies the behavior of Booking-related endpoints.
 */
public class BookingControllerTest {

    @Mock
    private BookingService bookingService;
    @Mock
    private UserService userService;

    @InjectMocks
    private BookingController bookingController;


    private MockMvc mockMvc;
    private ObjectMapper objectMapper;
    private Booking booking1;
    private Booking booking2;
    private List<Booking> bookingList;

    private Passenger passenger1;
    private Flight flight1;
    private Seat seat1;
    /**
     * Initializes mocks and test data before each test.
     */
    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(bookingController).build();

        objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());

        User user1 = new User();
        user1.setUserId(1L);
        user1.setUsername("testuser");

        passenger1 = new Passenger();
        flight1 = new Flight();
        seat1 = new Seat();

        booking1 = new Booking(1L, LocalDateTime.of(2024, 9, 24, 10, 0), passenger1, flight1, seat1, user1);
        booking2 = new Booking(2L, LocalDateTime.of(2024, 9, 25, 12, 30), passenger1, flight1, seat1, user1);
        bookingList = new ArrayList<>();
        bookingList.add(booking1);
        bookingList.add(booking2);

        when(bookingService.getBookingById(1L, user1)).thenReturn(booking1);
        when(bookingService.getBookingById(3L, user1)).thenReturn(null);
        when(bookingService.getAllBookings()).thenReturn(bookingList);
        when(bookingService.createBooking2(any(Booking.class))).thenReturn(booking1);
        when(bookingService.updateBooking2(eq(1L), any(Booking.class))).thenReturn(booking1);
        when(bookingService.updateBooking2(eq(3L), any(Booking.class))).thenReturn(null);
        when(bookingService.deleteBooking(1L)).thenReturn(true);
        when(bookingService.deleteBooking(4L)).thenReturn(false);
        when(userService.findByUsername("testuser")).thenReturn(user1);
    }
    /**
     * Tests the creation of a booking.
     * Verifies that a booking is created and returned with the expected properties.
     */
    @Test
    public void testCreateBooking2() throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());

        when(bookingService.createBooking2(any(Booking.class))).thenReturn(booking1);

        mockMvc.perform(post("/api/v1/bookings/create2")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(booking1)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.bookingId").value(1L))
                .andExpect(jsonPath("$.dateOfBooking").value("24-09-2024 10:00:00"));

        verify(bookingService, times(1)).createBooking2(any(Booking.class));
    }
    /**
     * Tests the retrieval of a booking by ID.
     * Verifies that the correct booking is returned with a 200 OK status.
     */
    @Test
    public void testGetBookingById() throws Exception {
        User user1 = new User();
        user1.setUserId(1L);
        user1.setUsername("testuser");

        when(bookingService.getBookingById(1L, user1)).thenReturn(booking1);
        when(userService.findByUsername(anyString())).thenReturn(user1);

        mockMvc.perform(get("/api/v1/bookings/{id}", 1L)
                        .principal(() -> "testuser"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.bookingId").value(1L));


        verify(bookingService, times(1)).getBookingById(1L,user1 );
    }
    /**
     * Tests the retrieval of all bookings for a specific user.
     * Verifies that the correct number of bookings is returned.
     */
    @Test
    public void testGetBookingById_NotFound() throws Exception {
        User user1 = new User();
        user1.setUserId(1L);
        user1.setUsername("testuser");

        when(bookingService.getBookingById(1L, user1)).thenReturn(booking1);
        when(userService.findByUsername(anyString())).thenReturn(user1);
        mockMvc.perform(get("/api/v1/bookings/{id}", 3L)
                        .principal(() -> "testuser"))
                .andExpect(status().isNotFound());

        verify(bookingService, times(1)).getBookingById(3L, user1);
    }
    /**
     * Tests the deletion of a booking.
     * Verifies that the correct response status is returned based on the booking's existence.
     */
    @Test
    public void testGetAllBookingsByUser() throws Exception {

        User testUser = new User(1L, "testUser", "password", "test@example.com", ERole.USER, bookingList);

        when(userService.findByUsername("testUser")).thenReturn(testUser);
        when(bookingService.getAllBookingsByUser(testUser)).thenReturn(bookingList);

        Principal mockPrincipal = () -> "testUser";

        mockMvc.perform(get("/api/v1/bookings/")
                        .principal(mockPrincipal))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].bookingId").value(1L))
                .andExpect(jsonPath("$[1].bookingId").value(2L));

        verify(bookingService, times(1)).getAllBookingsByUser(testUser);
    }
    @Test
    public void testGetAllBookings() throws Exception {
        when(bookingService.getAllBookings()).thenReturn(bookingList);

        mockMvc.perform(get("/api/v1/bookings/all"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].bookingId").value(1L))
                .andExpect(jsonPath("$[1].bookingId").value(2L));

        verify(bookingService, times(1)).getAllBookings();
    }
    @Test
    public void testUpdateBooking2() throws Exception {
        when(bookingService.updateBooking2(eq(1L), any(Booking.class))).thenReturn(booking1);

        mockMvc.perform(put("/api/v1/bookings/update/{id}", 1L)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(booking1))) // Usa el objectMapper configurado
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.bookingId").value(1L));

        verify(bookingService, times(1)).updateBooking2(eq(1L), any(Booking.class));
    }

    @Test
    public void testUpdateBooking_NotFound() throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());

        when(bookingService.updateBooking2(eq(3L), any(Booking.class))).thenReturn(null);

        mockMvc.perform(put("/api/v1/bookings/update/{id}", 3L)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(booking1)))
                .andExpect(status().isNotFound());

        verify(bookingService, times(1)).updateBooking2(eq(3L), any(Booking.class));
    }

    @Test
    public void testDeleteBooking() throws Exception {
        when(bookingService.deleteBooking(1L)).thenReturn(true);

        mockMvc.perform(delete("/api/v1/bookings/delete/{id}", 1L))
                .andExpect(status().isNoContent());

        verify(bookingService, times(1)).deleteBooking(1L);
    }

    @Test
    public void testDeleteBooking_NotFound() throws Exception {
        when(bookingService.deleteBooking(4L)).thenReturn(false);

        mockMvc.perform(delete("/api/v1/bookings/delete/{id}", 4L))
                .andExpect(status().isNotFound());

        verify(bookingService, times(1)).deleteBooking(4L);
    }


    @Test
    void createBooking() throws Exception {
        User user1 = new User();
        user1.setUserId(1L);
        user1.setUsername("testuser");

        Authentication authentication = mock(Authentication.class);
        when(authentication.getName()).thenReturn("testuser");

        when(userService.findByUsername("testuser")).thenReturn(user1);

        when(bookingService.createBooking(anyLong(), anyLong(), anyString(), eq(1L))).thenReturn(booking1);

        mockMvc.perform(post("/api/v1/bookings/create/1/1/1A/1")
                        .principal(authentication)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.bookingId").value(1L));

        verify(bookingService, times(1)).createBooking(1L, 1L, "1A", 1L);
    }





    @Test
    void updateBooking() throws Exception {
        when(bookingService.updateBooking(any(Booking.class))).thenReturn(booking1);

        String updatedBookingJson = objectMapper.writeValueAsString(booking1);

        mockMvc.perform(put("/api/v1/bookings/update/{id}", 1L)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(updatedBookingJson))
                .andExpect(status().isOk())
                .andExpect(content().json(updatedBookingJson));
    }


}