//package com.flightbookings.flight_bookings.controllers;
//
//import com.fasterxml.jackson.databind.ObjectMapper;
//import com.flightbookings.flight_bookings.models.Booking;
//import com.flightbookings.flight_bookings.models.Flight;
//import com.flightbookings.flight_bookings.models.Passenger;
//import com.flightbookings.flight_bookings.models.Seat;
//import com.flightbookings.flight_bookings.services.interfaces.BookingService;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.MockitoAnnotations;
//import org.springframework.http.MediaType;
//import org.springframework.test.web.servlet.MockMvc;
//import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
//import org.springframework.test.web.servlet.setup.MockMvcBuilders;
//
//import java.time.LocalDateTime;
//import java.util.ArrayList;
//import java.util.List;
//
//import static org.mockito.Mockito.*;
//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
//
//public class BookingControllerTest {
//
//    @Mock
//    private BookingService bookingService;
//
//    @InjectMocks
//    private BookingController bookingController;
//
//    private MockMvc mockMvc;
//    private ObjectMapper objectMapper;
//    private Booking booking1;
//    private Booking booking2;
//    private List<Booking> bookingList;
//
//    private Passenger passenger1;
//    private Flight flight1;
//    private Seat seat1;
//
//    @BeforeEach
//    public void setUp() {
//        MockitoAnnotations.openMocks(this);
//        mockMvc = MockMvcBuilders.standaloneSetup(bookingController).build();
//
//        objectMapper = new ObjectMapper();
//        objectMapper.registerModule(new JavaTimeModule());
//
//        passenger1 = new Passenger(); // Configure this object as needed
//        flight1 = new Flight(); // Configure this object as needed
//        seat1 = new Seat(); // Configure this object as needed
//
//        booking1 = new Booking(1L, LocalDateTime.of(2024, 9, 24, 10, 0), passenger1, flight1, seat1);
//        booking2 = new Booking(2L, LocalDateTime.of(2024, 9, 25, 12, 30), passenger1, flight1, seat1);
//        bookingList = new ArrayList<>();
//        bookingList.add(booking1);
//        bookingList.add(booking2);
//    }
//
//    @Test
//    public void testCreateBooking2() throws Exception {
//        ObjectMapper objectMapper = new ObjectMapper();
//        objectMapper.registerModule(new JavaTimeModule());
//
//        when(bookingService.createBooking2(any(Booking.class))).thenReturn(booking1);
//
//        mockMvc.perform(post("/api/v1/bookings/create2")
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content(objectMapper.writeValueAsString(booking1)))
//                .andExpect(status().isCreated())
//                .andExpect(jsonPath("$.bookingId").value(1L))
//                .andExpect(jsonPath("$.dateOfBooking").value("24-09-2024 10:00:00")); // Asegúrate de que el formato coincide
//
//        verify(bookingService, times(1)).createBooking2(any(Booking.class));
//    }
//
//    @Test
//    public void testGetBookingById() throws Exception {
//        when(bookingService.getBookingById(1L)).thenReturn(booking1);
//
//        mockMvc.perform(get("/api/v1/bookings/{id}", 1L))
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$.bookingId").value(1L));
//
//        verify(bookingService, times(1)).getBookingById(1L);
//    }
//
//    @Test
//    public void testGetBookingById_NotFound() throws Exception {
//        when(bookingService.getBookingById(3L)).thenReturn(null);
//
//        mockMvc.perform(get("/api/v1/bookings/{id}", 3L))
//                .andExpect(status().isNotFound());
//
//        verify(bookingService, times(1)).getBookingById(3L);
//    }
//
//    @Test
//    public void testGetAllBookings() throws Exception {
//        when(bookingService.getAllBookings()).thenReturn(bookingList);
//
//
//        mockMvc.perform(get("/api/v1/bookings/"))
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$[0].bookingId").value(1L))
//                .andExpect(jsonPath("$[1].bookingId").value(2L));
//
//        verify(bookingService, times(1)).getAllBookings();
//    }
//
//    @Test
//    public void testUpdateBooking2() throws Exception {
//        when(bookingService.updateBooking2(eq(1L), any(Booking.class))).thenReturn(booking1);
//
//        mockMvc.perform(put("/api/v1/bookings/update/{id}", 1L)
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content(objectMapper.writeValueAsString(booking1))) // Usa el objectMapper configurado
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$.bookingId").value(1L));
//
//        verify(bookingService, times(1)).updateBooking2(eq(1L), any(Booking.class));
//    }
//
//    @Test
//    public void testUpdateBooking_NotFound() throws Exception {
//        ObjectMapper objectMapper = new ObjectMapper();
//        objectMapper.registerModule(new JavaTimeModule());
//
//        when(bookingService.updateBooking2(eq(3L), any(Booking.class))).thenReturn(null);
//
//        mockMvc.perform(put("/api/v1/bookings/update/{id}", 3L)
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content(objectMapper.writeValueAsString(booking1)))
//                .andExpect(status().isNotFound());
//
//        verify(bookingService, times(1)).updateBooking2(eq(3L), any(Booking.class));
//    }
//
//    @Test
//    public void testDeleteBooking() throws Exception {
//        when(bookingService.deleteBooking(1L)).thenReturn(true);
//
//        mockMvc.perform(delete("/api/v1/bookings/delete/{id}", 1L))
//                .andExpect(status().isNoContent());
//
//        verify(bookingService, times(1)).deleteBooking(1L);
//    }
//
//    @Test
//    public void testDeleteBooking_NotFound() throws Exception {
//        when(bookingService.deleteBooking(4L)).thenReturn(false);
//
//        mockMvc.perform(delete("/api/v1/bookings/delete/{id}", 4L))
//                .andExpect(status().isNotFound());
//
//        verify(bookingService, times(1)).deleteBooking(4L);
//    }
//
//
//    @Test
//    void createBooking() throws Exception {
//        when(bookingService.createBooking(anyLong(), anyLong(), anyString())).thenReturn(booking1);
//
//        String bookingJson = "{"
//                + "\"flightId\": 1,"
//                + "\"passengerId\": 1,"
//                + "\"seatName\": \"1A\""
//                + "}";
//
//        mockMvc.perform(post("/api/v1/booking")
//                        .param("flightId", "1")
//                        .param("passengerId", "1")
//                        .param("seatName", "1A")
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content(bookingJson))
//                .andExpect(status().isCreated())
//                .andExpect(content().json(objectMapper.writeValueAsString(booking1)));
//    }
//
//
//
//    @Test
//    void updateBooking() throws Exception {
//        when(bookingService.updateBooking(any(Booking.class))).thenReturn(booking1);
//
//        String updatedBookingJson = objectMapper.writeValueAsString(booking1);
//
//        mockMvc.perform(put("/api/v1/booking/{id}", 1L)
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content(updatedBookingJson))
//                .andExpect(status().isOk())
//                .andExpect(content().json(updatedBookingJson));
//    }
//
//
//}