package com.flightbookings.flight_bookings.controllers;

import com.flightbookings.flight_bookings.models.BookingEntity;
import com.flightbookings.flight_bookings.models.UserEntity;
import com.flightbookings.flight_bookings.services.interfaces.BookingService;
import com.flightbookings.flight_bookings.services.interfaces.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
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
@RequiredArgsConstructor
public class BookingController {
    private final BookingService bookingService;
    private final UserService userService;

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
    public ResponseEntity<BookingEntity> createBooking(@PathVariable("flightId") Long flightId,
                                                       @PathVariable("passengerId") Long passengerId,
                                                       @PathVariable("seatName") String seatName,
                                                       Principal principal) {
        if (principal == null) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }

        UserEntity user = userService.findByUsername(principal.getName());
        BookingEntity booking = bookingService.createBooking(flightId, passengerId,
                seatName, user.getUserId());
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
    public ResponseEntity<BookingEntity> getBookingByIdByUser(@Parameter(description = "ID of the booking  to be retrieved")@PathVariable Long id, Principal principal) {
        UserEntity user = userService.findByUsername(principal.getName());
        BookingEntity booking = bookingService.getBookingByIdByUser(id, user);

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
    public ResponseEntity<List<BookingEntity>> getAllBookingsByUser(Principal principal) {
        UserEntity user = userService.findByUsername(principal.getName());
        List<BookingEntity> bookings = bookingService.getAllBookingsByUser(user);
        return new ResponseEntity<>(bookings, HttpStatus.OK);
    }
    /**
     * Retrieves all bookings.
     *
     * @return the list of bookings.
     */
    @Operation(summary = "Get all bookings")
    @GetMapping("/all")
    public ResponseEntity<List<BookingEntity>> getAllBookings() {
        List<BookingEntity> bookings = bookingService.getAllBookings();
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
    public ResponseEntity<BookingEntity> updateBooking(@Parameter(description = "ID of the booking  to be retrieved") @PathVariable Long id, @RequestBody BookingEntity updatedBooking) {
        updatedBooking.setBookingId(id);
        BookingEntity booking = bookingService.updateBooking(updatedBooking);
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
