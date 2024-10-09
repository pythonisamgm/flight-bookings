package com.flightbookings.flight_bookings.controllers;

import com.flightbookings.flight_bookings.dtos.DTOBooking.BookingDTO;
import com.flightbookings.flight_bookings.models.*;
import com.flightbookings.flight_bookings.services.interfaces.BookingService;
import com.flightbookings.flight_bookings.services.interfaces.UserService;
import com.flightbookings.flight_bookings.dtos.DTOBooking.BookingConverter;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.*;
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
    private Booking booking;
    private BookingDTO bookingDTO;
    private Principal mockPrincipal;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(bookingController).build();

        booking = new Booking();
        booking.setBookingId(1L);

        booking = new Booking();

        Passenger passenger = new Passenger();
        passenger.setPassengerId(200L);
        booking.setPassenger(passenger);

        Flight flight = new Flight();
        flight.setFlightId(100L);
        booking.setFlight(flight);

        Seat seat = new Seat();
        seat.setSeatName("A1");
        booking.setSeat(seat);

        User user = new User();
        user.setUserId(300L);
        booking.setUser(user);

        bookingDTO = new BookingDTO();
        bookingDTO.setBookingId(1L);
        bookingDTO.setFlightId(100L);
        bookingDTO.setPassengerId(200L);
        bookingDTO.setSeatName("A1");
        bookingDTO.setUserId(300L);

        when(userService.findByUsername(anyString())).thenReturn(user);
        when(bookingService.createBooking(anyLong(), anyLong(), anyString(), anyLong())).thenReturn(booking);
        when(bookingConverter.bookingToDto(any(Booking.class))).thenReturn(bookingDTO);

        mockPrincipal = () -> "username";
    }

    @Test
    void createBooking() throws Exception {
        Flight flight = new Flight();
        flight.setFlightId(100L);

        Passenger passenger = new Passenger();
        passenger.setPassengerId(200L);

        Seat seat = new Seat();
        seat.setSeatName("A1");

        User user = new User();
        user.setUserId(300L);

        Booking booking = new Booking();
        booking.setBookingId(1L);
        booking.setFlight(flight);
        booking.setPassenger(passenger);
        booking.setSeat(seat);
        booking.setUser(user);

        when(userService.findByUsername(anyString())).thenReturn(user);
        when(bookingService.createBooking(anyLong(), anyLong(), anyString(), anyLong())).thenReturn(booking);
        when(bookingConverter.bookingToDto(any(Booking.class))).thenCallRealMethod(); // Asegúrate de que llame al método real

        mockMvc.perform(post("/api/v1/bookings/create/100/200/A1")
                        .principal(mockPrincipal)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.bookingId").value(1L))
                .andExpect(jsonPath("$.flightId").value(100L))
                .andExpect(jsonPath("$.passengerId").value(200L))
                .andExpect(jsonPath("$.seatName").value("A1"))
                .andExpect(jsonPath("$.userId").value(300L));

        verify(bookingService, times(1)).createBooking(anyLong(), anyLong(), anyString(), anyLong());
    }




    @Test
    void getBookingById() throws Exception {
        when(userService.findByUsername(anyString())).thenReturn(new User());
        when(bookingService.getBookingById(anyLong(), any(User.class))).thenReturn(booking);
        when(bookingConverter.bookingToDto(any(Booking.class))).thenReturn(bookingDTO);

        mockMvc.perform(get("/api/v1/bookings/1")
                        .principal(mockPrincipal)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.bookingId").value(1L))
                .andExpect(jsonPath("$.flightId").value(100L))
                .andExpect(jsonPath("$.seatName").value("A1"));

        verify(bookingService, times(1)).getBookingById(anyLong(), any(User.class));
    }
    @Test
    void getAllBookingsByUser() throws Exception {
        when(userService.findByUsername(anyString())).thenReturn(new User());
        List<Booking> bookings = List.of(booking);
        List<BookingDTO> bookingDTOs = List.of(bookingDTO);

        when(bookingService.getAllBookingsByUser(any(User.class))).thenReturn(bookings);
        when(bookingConverter.bookingsToDtoList(bookings)).thenReturn(bookingDTOs);

        mockMvc.perform(get("/api/v1/bookings/")
                        .principal(mockPrincipal)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].bookingId").value(1L))
                .andExpect(jsonPath("$[0].flightId").value(100L))
                .andExpect(jsonPath("$[0].seatName").value("A1"));

        verify(bookingService, times(1)).getAllBookingsByUser(any(User.class));
    }
    @Test
    void getAllBookings() throws Exception {
        List<Booking> bookings = List.of(booking);
        List<BookingDTO> bookingDTOs = List.of(bookingDTO);

        when(bookingService.getAllBookings()).thenReturn(bookings);
        when(bookingConverter.bookingsToDtoList(bookings)).thenReturn(bookingDTOs);

        mockMvc.perform(get("/api/v1/bookings/all")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].bookingId").value(1L))
                .andExpect(jsonPath("$[0].flightId").value(100L))
                .andExpect(jsonPath("$[0].seatName").value("A1"));

        verify(bookingService, times(1)).getAllBookings();
    }
    @Test
    void updateBooking() throws Exception {
        when(bookingService.updateBooking(any(Booking.class))).thenReturn(booking);
        when(bookingConverter.bookingToDto(any(Booking.class))).thenReturn(bookingDTO);

        mockMvc.perform(put("/api/v1/bookings/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"seatName\": \"A1\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.bookingId").value(1L))
                .andExpect(jsonPath("$.flightId").value(100L))
                .andExpect(jsonPath("$.seatName").value("A1"));

        verify(bookingService, times(1)).updateBooking(any(Booking.class));
    }

    @Test
    void deleteBooking() throws Exception {
        when(bookingService.deleteBooking(anyLong())).thenReturn(true);

        mockMvc.perform(delete("/api/v1/bookings/delete/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());

        verify(bookingService, times(1)).deleteBooking(1L);
    }
}
