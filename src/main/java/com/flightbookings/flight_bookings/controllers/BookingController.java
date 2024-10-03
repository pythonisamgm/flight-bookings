package com.flightbookings.flight_bookings.controllers;

import com.flightbookings.flight_bookings.dtos.DTOBooking.BookingDTO;
import com.flightbookings.flight_bookings.dtos.DTOUser.UserDTO;
import com.flightbookings.flight_bookings.models.User;
import com.flightbookings.flight_bookings.services.UserServiceImpl;
import com.flightbookings.flight_bookings.services.interfaces.BookingService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

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
    private final UserServiceImpl userService;

    /**
     * Constructor to initialize the BookingController with BookingService and UserService.
     *
     * @param bookingService the service for booking management.
     * @param userService    the service for user management.
     */
    public BookingController(BookingService bookingService, UserServiceImpl userService) {
        this.bookingService = bookingService;
        this.userService = userService;
    }

    /**
     * Creates a new booking.
     *
     * @param flightId    the ID of the flight.
     * @param passengerId the ID of the passenger.
     * @param seatName    the name of the seat.
     * @param authentication the authentication object to retrieve the current user.
     * @return the created booking.
     */
    @Operation(summary = "Create a new booking")
    @PostMapping(consumes = "application/json")
    public ResponseEntity<BookingDTO> createBooking(
            @Parameter(description = "Flight ID") @RequestParam Long flightId,
            @Parameter(description = "Passenger ID") @RequestParam Long passengerId,
            @Parameter(description = "Seat name") @RequestParam String seatName,
            @AuthenticationPrincipal Authentication authentication) {

        UserDTO user = userService.findByUsername(authentication.getName());
        BookingDTO createdBooking = bookingService.createBooking(flightId, passengerId, seatName, user);
        return new ResponseEntity<>(createdBooking, HttpStatus.CREATED);
    }

    /**
     * Retrieves a booking by its ID.
     *
     * @param id       the ID of the booking.
     * @param principal the principal object to get the current user.
     * @return the booking if found, otherwise a 404 response.
     */
    @Operation(summary = "Get booking by ID")
    @GetMapping("/{id}")
    public ResponseEntity<BookingDTO> getBookingById(
            @Parameter(description = "ID of the booking to be retrieved") @PathVariable Long id,
            Principal principal) {

        User user = userService.findByUsername(principal.getName());
        BookingDTO bookingDTO = bookingService.getBookingById(id, user);

        if (bookingDTO != null) {
            return new ResponseEntity<>(bookingDTO, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    /**
     * Retrieves all bookings for the current user.
     *
     * @param authentication the authentication object to get the current user.
     * @return the list of bookings.
     */
    @Operation(summary = "Get all bookings for the current user")
    @GetMapping
    public ResponseEntity<List<BookingDTO>> getAllBookings(@AuthenticationPrincipal Authentication authentication) {
        User user = userService.findByUsername(authentication.getName());
        List<BookingDTO> bookings = bookingService.getAllBookings(user);
        return new ResponseEntity<>(bookings, HttpStatus.OK);
    }

    /**
     * Updates an existing booking.
     *
     * @param id             the ID of the booking to be updated.
     * @param updatedBooking the booking object with updated details.
     * @return the updated booking.
     */
    @Operation(summary = "Update an existing booking")
    @PutMapping("/{id}")
    public ResponseEntity<BookingDTO> updateBooking(
            @Parameter(description = "ID of the booking to be updated") @PathVariable Long id,
            @RequestBody BookingDTO updatedBookingDTO) {

        BookingDTO updatedBooking = bookingService.updateBooking(updatedBookingDTO);
        if (updatedBooking != null) {
            return new ResponseEntity<>(updatedBooking, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    /**
     * Deletes an existing booking by ID.
     *
     * @param id the ID of the booking to be deleted.
     * @return a 204 response if deleted, otherwise 404.
     */
    @Operation(summary = "Delete existing booking by ID")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBooking(
            @Parameter(description = "ID of the booking to be deleted") @PathVariable Long id) {

        boolean isDeleted = bookingService.deleteBooking(id);
        if (isDeleted) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
