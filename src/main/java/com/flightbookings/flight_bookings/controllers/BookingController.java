package com.flightbookings.flight_bookings.controllers;

import com.flightbookings.flight_bookings.models.Booking;
import com.flightbookings.flight_bookings.services.interfaces.BookingService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@CrossOrigin("*")
@RestController
@RequestMapping("/api/v1/bookings")
@Api(tags = "Booking Management", description = "Operations pertaining to booking management")
public class BookingController {
    private final BookingService bookingService;

    public BookingController(BookingService bookingService) {
        this.bookingService = bookingService;
    }

    @ApiOperation(value = "Create a new booking", response = Booking.class)
    @PostMapping(value="/create",consumes = "application/json")
    public ResponseEntity<Booking> createBooking(@RequestParam Long flightId,
                                                 @RequestParam Long passengerId,
                                                 @RequestParam String seatName) {
        Booking booking = bookingService.createBooking(flightId, passengerId, seatName);
        return new ResponseEntity<>(booking, HttpStatus.CREATED);
    }
    @ApiOperation(value = "Update existing booking", response = Booking.class)
    @PutMapping("/{id}")
    public ResponseEntity<Booking> updateBooking(@PathVariable Long id, @RequestBody Booking updatedBooking) {
        updatedBooking.setBookingId(id);
        Booking booking = bookingService.updateBooking(updatedBooking);
        return new ResponseEntity<>(booking, HttpStatus.OK);
    }

    @ApiOperation(value = "Create a new booking. Version 1", response = Booking.class)
    @PostMapping(value="/create2",consumes = "application/json")
    public ResponseEntity<Booking> createBooking2(@RequestBody Booking booking) {
        Booking newBooking = bookingService.createBooking2(booking);
        return new ResponseEntity<>(newBooking, HttpStatus.CREATED);
    }

    @ApiOperation(value = "Get booking by ID", response = Booking.class)
    @GetMapping("/{id}")
    public ResponseEntity<Booking> getBookingById(@PathVariable Long id) {
        Booking booking = bookingService.getBookingById(id);
        if (booking != null) {
            return new ResponseEntity<>(booking, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @ApiOperation(value = "Get all bookings", response = Booking.class)
    @GetMapping("/")
    public ResponseEntity<List<Booking>> getAllBookings() {
        List<Booking> bookings = bookingService.getAllBookings();
        return new ResponseEntity<>(bookings, HttpStatus.OK);
    }

    @ApiOperation(value = "Update an existing booking-Version 1", response = Booking.class)
    @PutMapping("/update/{id}")
    public ResponseEntity<Booking> updateBooking2(@PathVariable Long id, @RequestBody Booking bookingDetails) {
        Booking updatedBooking = bookingService.updateBooking2(id, bookingDetails);
        if (updatedBooking != null) {
            return new ResponseEntity<>(updatedBooking, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
    @ApiOperation(value = "Delete existing booking by ID", response = Booking.class)
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
