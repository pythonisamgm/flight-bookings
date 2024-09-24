package com.flightbookings.flight_bookings.controllers;

import com.flightbookings.flight_bookings.services.UserServiceImpl;
import com.flightbookings.flight_bookings.models.Booking;
import com.flightbookings.flight_bookings.models.User;
import com.flightbookings.flight_bookings.services.BookingServiceImpl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/bookings")
public class BookingController {

    private final BookingServiceImpl bookingService;
    private final UserServiceImpl userService;

    public BookingController(BookingServiceImpl bookingService, UserServiceImpl userService) {
        this.bookingService = bookingService;
        this.userService = userService;
    }

    @PostMapping("/create")
    public ResponseEntity<Booking> createBooking(@RequestBody Booking booking) {
        User user = userService.getUserByUsername(SecurityContextHolder.getContext().getAuthentication().getName());
        Booking createdBooking = bookingService.createBooking(booking, user);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdBooking);
    }

    @GetMapping("/user/{id}")
    public ResponseEntity<List<Booking>> getBookingsByUser(@PathVariable Long id) {
        User user = userService.getUserById(id); // Obtener el usuario por id si es necesario
        List<Booking> bookings = bookingService.getBookingsByUser(user); // Cambia esto según tu lógica
        return new ResponseEntity<>(bookings, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Booking> getBookingById(@PathVariable Long id) {
        Booking booking = bookingService.getBookingById(id);
        if (booking != null) {
            return new ResponseEntity<>(booking, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/")
    public ResponseEntity<List<Booking>> getAllBookings() {
        List<Booking> bookings = bookingService.getAllBookings();
        return new ResponseEntity<>(bookings, HttpStatus.OK);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<Booking> updateBooking(@PathVariable Long id, @RequestBody Booking booking) {
        Booking updatedBooking = bookingService.updateBooking(id, booking);
        if (updatedBooking != null) {
            return ResponseEntity.ok(updatedBooking);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteBooking(@PathVariable Long id) {
        boolean isDeleted = bookingService.deleteBooking(id);
        if (isDeleted) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
