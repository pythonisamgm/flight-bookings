package com.flightbookings.flight_bookings.controllers;

import com.flightbookings.flight_bookings.dtos.DTOBooking.BookingConverter;
import com.flightbookings.flight_bookings.dtos.DTOBooking.BookingDTO;
import com.flightbookings.flight_bookings.models.Booking;
import com.flightbookings.flight_bookings.models.User;
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
    private final BookingConverter bookingConverter;

    /**
     * Constructor to initialize the BookingController with BookingService, UserService, and BookingConverter.
     *
     * @param bookingService the service for booking management.
     * @param userService    the service for user management.
     * @param bookingConverter the converter for Booking and BookingDTO.
     */
    public BookingController(BookingService bookingService, UserService userService, BookingConverter bookingConverter) {
        this.bookingService = bookingService;
        this.userService = userService;
        this.bookingConverter = bookingConverter;
    }

    /**
     * Creates a new booking.
     *
     * @param bookingDTO    the BookingDTO containing booking details.
     * @param authentication the authentication object to retrieve the current user.
     * @return the created booking.
     */
    @Operation(summary = "Create a new booking")
    @PostMapping(value = "/create")
    public ResponseEntity<BookingDTO> createBooking(@RequestBody BookingDTO bookingDTO,
                                                    @AuthenticationPrincipal Authentication authentication) {
        User user = userService.findByUsername(authentication.getName());
        Booking booking = bookingConverter.dtoToBooking(bookingDTO);
        booking.setUser(user);

        Booking newBooking = bookingService.createBooking(booking);
        BookingDTO newBookingDTO = bookingConverter.bookingToDto(newBooking);

        return new ResponseEntity<>(newBookingDTO, HttpStatus.CREATED);
    }

    /**
     * Updates an existing booking.
     *
     * @param id             the ID of the booking to be updated.
     * @param updatedBookingDTO the BookingDTO with updated details.
     * @return the updated booking.
     */
    @Operation(summary = "Update existing booking")
    @PutMapping("/{id}")
    public ResponseEntity<BookingDTO> updateBooking(@Parameter(description = "ID of the booking to be updated")
                                                    @PathVariable Long id,
                                                    @RequestBody BookingDTO updatedBookingDTO) {
        Booking updatedBooking = bookingConverter.dtoToBooking(updatedBookingDTO);
        updatedBooking.setBookingId(id);

        Booking booking = bookingService.updateBooking(updatedBooking);
        BookingDTO bookingDTO = bookingConverter.bookingToDto(booking);

        return new ResponseEntity<>(bookingDTO, HttpStatus.OK);
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
    public ResponseEntity<BookingDTO> getBookingById(@Parameter(description = "ID of the booking to be retrieved")
                                                     @PathVariable Long id,
                                                     Principal principal) {
        User user = userService.findByUsername(principal.getName());
        Booking booking = bookingService.getBookingById(id, user);

        if (booking != null) {
            BookingDTO bookingDTO = bookingConverter.bookingToDto(booking);
            return new ResponseEntity<>(bookingDTO, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    /**
     * Retrieves all bookings for the current user.
     *
     * @return the list of bookings.
     */
    @Operation(summary = "Get all the bookings for the current user")
    @GetMapping("/")
    public ResponseEntity<List<BookingDTO>> getAllBookings(Principal principal) {
        User user = userService.findByUsername(principal.getName());
        List<Booking> bookings = bookingService.getAllBookingsByUser(user);

        List<BookingDTO> bookingDTOs = bookingConverter.bookingsToDtoList(bookings);
        return new ResponseEntity<>(bookingDTOs, HttpStatus.OK);
    }

    /**
     * Deletes an existing booking by ID.
     *
     * @param id the ID of the booking to be deleted.
     * @return a 204 response if deleted, otherwise 404.
     */
    @Operation(summary = "Delete existing booking by ID")
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteBooking(@Parameter(description = "ID of the booking to be deleted") @PathVariable Long id) {
        boolean isDeleted = bookingService.deleteBooking(id);
        if (isDeleted) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
