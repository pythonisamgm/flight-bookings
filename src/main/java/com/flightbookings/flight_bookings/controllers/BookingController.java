package com.flightbookings.flight_bookings.controllers;

import com.flightbookings.flight_bookings.models.Booking;
import com.flightbookings.flight_bookings.models.User;
import com.flightbookings.flight_bookings.services.interfaces.BookingService;
import com.flightbookings.flight_bookings.services.interfaces.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;
import java.util.List;
/**
 * Controller for managing bookings, including creating, updating, retrieving, and deleting bookings.
 */
@CrossOrigin("*")
@RestController
@RequestMapping("/api/v1/bookings")
@Tag(name = "Booking Management", description = "Operations pertaining to booking management")
public class BookingController {
    private final BookingService bookingService;
    private final UserService userService;
    /**3
     * Constructor to initialize the BookingController with BookingService and UserService.
     *
     * @param bookingService the service for booking management.
     * @param userService    the service for user management.
     */
    public BookingController(BookingService bookingService, UserService userService) {
        this.bookingService = bookingService;
        this.userService = userService;
    }
    /**
     * Creates a new booking.
     *
     * @param flightId    the ID of the flight.
     * @param passengerId the ID of the passenger.
     * @param seatName    the name of the seat.
     * @return the created booking.
     */
    @Operation(summary = "Create a new booking")
    @PostMapping(value = "/create/{flightId}/{passengerId}/{seatName}")
    public ResponseEntity<Booking> createBooking(@PathVariable("flightId") Long flightId,
                                                 @PathVariable("passengerId") Long passengerId,
                                                 @PathVariable("seatName") String seatName,
                                                 Principal principal) {
        if (principal == null) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }

        User user = userService.findByUsername(principal.getName());
        Booking booking = bookingService.createBooking(flightId, passengerId, seatName, user.getUserId());
        return new ResponseEntity<>(booking, HttpStatus.CREATED);
    }
    /**
     * Retrieves a booking by its ID.
     *
     * @param id       the ID of the booking.
     * @param principal the principal object to get the current user.
     * @return the booking if found, otherwise a 404 response.
     */
    @Operation(summary =  "Get booking by ID")
    @GetMapping("/{id}")
    public ResponseEntity<Booking> getBookingByIdByUser(@Parameter(description = "ID of the booking  to be retrieved")@PathVariable Long id, Principal principal) {
        User user = userService.findByUsername(principal.getName());
        Booking booking = bookingService.getBookingByIdByUser(id, user);

        if (booking != null) {
            return new ResponseEntity<>(booking, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
    /**
     * Retrieves all bookings for the current user.
     *
     *
     * @return the list of bookings.
     */
    @Operation(summary =  "Get all the bookings for the current user")
    @GetMapping("/")
    public ResponseEntity<List<Booking>> getAllBookingsByUser(Principal principal) {
        User user = userService.findByUsername(principal.getName());
        List<Booking> bookings = bookingService.getAllBookingsByUser(user);
        return new ResponseEntity<>(bookings, HttpStatus.OK);
    }
    /**
     * Retrieves all bookings.
     *
     * @return the list of bookings.
     */
    @Operation(summary = "Get all bookings")
    @GetMapping("/all")
    public ResponseEntity<List<Booking>> getAllBookings() {
        List<Booking> bookings = bookingService.getAllBookings();
        return new ResponseEntity<>(bookings, HttpStatus.OK);
    }
    /**
     * Updates an existing booking.
     *
     * @param id             the ID of the booking to be updated.
     * @param updatedBooking the booking object with updated details.
     * @return the updated booking.
     */
    @Operation(summary =  "Update existing booking")
    @PutMapping("/update/{id}")
    public ResponseEntity<Booking> updateBooking(@Parameter(description = "ID of the booking  to be retrieved") @PathVariable Long id, @RequestBody Booking updatedBooking) {
        updatedBooking.setBookingId(id);
        Booking booking = bookingService.updateBooking(updatedBooking);
        return new ResponseEntity<>(booking, HttpStatus.OK);
    }
    /**
     * Deletes an existing booking by ID.
     *
     * @param id the ID of the booking to be deleted.
     * @return a 204 response if deleted, otherwise 404.
     */
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
