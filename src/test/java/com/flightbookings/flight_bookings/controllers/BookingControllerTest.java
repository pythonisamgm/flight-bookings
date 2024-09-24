package com.flightbookings.flight_bookings.controllers;

import com.flightbookings.flight_bookings.models.Booking;
import com.flightbookings.flight_bookings.models.User;
import com.flightbookings.flight_bookings.services.BookingServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class BookingControllerTest {

    @Mock
    private BookingServiceImpl bookingService;

    @InjectMocks
    private BookingController bookingController;

    private MockMvc mockMvc;

    private List<Booking> bookingList = new ArrayList<>();
    private Booking booking1;
    private Booking booking2;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(bookingController).build();

        User User = new User();
        Booking booking1 = new Booking();
        Booking booking2 = new Booking();
    }


    @Test
    void createBooking() throws Exception {
        String courseJson = "{"
                + "\"id\": 1,"
                + "}";

    }

    @Test
    void getAllBookings() throws Exception {
    }


    @Test
    void getBookingById() throws Exception {
    }

    @Test
    void updateBooking() throws Exception {
    }


    @Test
    void deleteBookingById() throws Exception {
    }

    @Test
    void deleteAllBookings() throws Exception {
    }
}
