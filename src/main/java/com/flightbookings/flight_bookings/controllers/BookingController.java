package com.flightbookings.flight_bookings.controllers;


import com.flightbookings.flight_bookings.models.Booking;
import com.flightbookings.flight_bookings.services.interfaces.BookingService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin("*")
@RestController
@RequestMapping("api/v1/booking")
public class BookingController {
    private final BookingService bookingService;

    public BookingController(BookingService bookingService) {
        this.bookingService = bookingService;
    }

    @PostMapping
    public ResponseEntity<Booking> createBooking(@RequestParam Long flightId,
                                                 @RequestParam Long passengerId,
                                                 @RequestParam String seatName) {
        Booking booking = bookingService.createBooking(flightId, passengerId, seatName);
        return new ResponseEntity<>(booking, HttpStatus.CREATED);
    }
    @PutMapping("/{id}")
    public ResponseEntity<Booking> updateBooking(@PathVariable Long id, @RequestBody Booking updatedBooking) {
        updatedBooking.setBookingId(id);
        Booking booking = bookingService.updateBooking(updatedBooking);
        return new ResponseEntity<>(booking, HttpStatus.OK);
    }
}
