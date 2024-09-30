package com.flightbookings.flight_bookings.controllers;

import com.flightbookings.flight_bookings.models.Booking;
import com.flightbookings.flight_bookings.models.User;
import com.flightbookings.flight_bookings.services.UserServiceImpl;
import com.flightbookings.flight_bookings.services.interfaces.BookingService;
import com.flightbookings.flight_bookings.services.interfaces.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;
import java.util.List;

@CrossOrigin("*")
@RestController
@RequestMapping("/api/v1/bookings")
@Tag(name = "Booking Management", description = "Operations pertaining to booking management")
public class BookingController {
    private final BookingService bookingService;
    private final UserServiceImpl userService;

    public BookingController(BookingService bookingService, UserServiceImpl userService) {
        this.bookingService = bookingService;
        this.userService = userService;
    }

    @Operation(summary =  "Create a new booking")
    @PostMapping(value="/create",consumes = "application/json")
    public ResponseEntity<Booking> createBooking(@RequestParam Long flightId,
                                                 @RequestParam Long passengerId,
                                                 @RequestParam String seatName,
                                                 @AuthenticationPrincipal Authentication authentication) {
        User user = userService.findByUsername(authentication.getName());
        Booking booking = bookingService.createBooking(flightId, passengerId, seatName, user);
        return new ResponseEntity<>(booking, HttpStatus.CREATED);
    }
    @Operation(summary =  "Update existing booking")
    @PutMapping("/{id}")
    public ResponseEntity<Booking> updateBooking(@Parameter(description = "ID of the booking  to be retrieved") @PathVariable Long id, @RequestBody Booking updatedBooking) {
        updatedBooking.setBookingId(id);
        Booking booking = bookingService.updateBooking(updatedBooking);
        return new ResponseEntity<>(booking, HttpStatus.OK);
    }

    @Operation(summary =  "Create a new booking. Version 1")
    @PostMapping(value="/create2",consumes = "application/json")
    public ResponseEntity<Booking> createBooking2(@RequestBody Booking booking) {
        Booking newBooking = bookingService.createBooking2(booking);
        return new ResponseEntity<>(newBooking, HttpStatus.CREATED);
    }

    @Operation(summary =  "Get booking by ID")
    @GetMapping("/{id}")
    public ResponseEntity<Booking> getBookingById(@Parameter(description = "ID of the booking  to be retrieved")@PathVariable Long id, Principal principal) {
        User user = userService.findByUsername(principal.getName());
        Booking booking = bookingService.getBookingById(id, user);

        if (booking != null) {
            return new ResponseEntity<>(booking, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @Operation(summary =  "Get all bookings")
    @GetMapping("/")
    public ResponseEntity<List<Booking>> getAllBookings(@AuthenticationPrincipal Authentication authentication) {
        User user = userService.findByUsername(authentication.getName());
        List<Booking> bookings = bookingService.getAllBookings(user);
        return new ResponseEntity<>(bookings, HttpStatus.OK);
    }

    @Operation(summary =  "Update an existing booking-Version 1")
    @PutMapping("/update/{id}")
    public ResponseEntity<Booking> updateBooking2(@Parameter(description = "ID of the booking  to be retrieved")@PathVariable Long id, @RequestBody Booking bookingDetails) {
        Booking updatedBooking = bookingService.updateBooking2(id, bookingDetails);
        if (updatedBooking != null) {
            return new ResponseEntity<>(updatedBooking, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
    @Operation(summary =  "Delete existing booking by ID")
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteBooking(@Parameter(description = "ID of the booking  to be retrieved") @PathVariable Long id) {
        boolean isDeleted = bookingService.deleteBooking(id);
        if (isDeleted) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
