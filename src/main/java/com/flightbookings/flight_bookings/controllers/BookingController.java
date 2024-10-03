package com.flightbookings.flight_bookings.controllers;

import com.flightbookings.flight_bookings.dtos.DTOBooking.BookingDTO;
import com.flightbookings.flight_bookings.dtos.DTOUser.UserDTO;
import com.flightbookings.flight_bookings.services.interfaces.BookingService;
import com.flightbookings.flight_bookings.services.interfaces.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/bookings")
public class BookingController {

    private final BookingService bookingService;
    private final UserService userService;

    public BookingController(BookingService bookingService, UserService userService) {
        this.bookingService = bookingService;
        this.userService = userService;
    }

    @Operation(summary = "Get all bookings for the authenticated user")
    @GetMapping
    public ResponseEntity<List<BookingDTO>> getAllBookings(@AuthenticationPrincipal UserDetails userDetails) {
        UserDTO user = userService.findByUsername(userDetails.getUsername());
        List<BookingDTO> bookings = bookingService.getAllBookings(user);
        return new ResponseEntity<>(bookings, HttpStatus.OK);
    }

    @Operation(summary = "Get booking by ID")
    @GetMapping("/{id}")
    public ResponseEntity<BookingDTO> getBookingById(
            @Parameter(description = "ID of the booking to be retrieved") @PathVariable Long id,
            @AuthenticationPrincipal UserDetails userDetails) {
        UserDTO user = userService.findByUsername(userDetails.getUsername());
        BookingDTO bookingDTO = bookingService.getBookingById(id, user);

        if (bookingDTO != null) {
            return new ResponseEntity<>(bookingDTO, HttpStatus.OK);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @Operation(summary = "Create a new booking")
    @PostMapping
    public ResponseEntity<BookingDTO> createBooking(
            @Parameter(description = "Booking details") @RequestBody BookingDTO bookingDTO,
            @AuthenticationPrincipal UserDetails userDetails) {
        UserDTO user = userService.findByUsername(userDetails.getUsername());
        bookingDTO.setUserId(user.getUserId());
        BookingDTO createdBooking = bookingService.createBooking(bookingDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdBooking);
    }

    @Operation(summary = "Update an existing booking")
    @PutMapping("/{id}")
    public ResponseEntity<BookingDTO> updateBooking(
            @Parameter(description = "ID of the booking to be updated") @PathVariable Long id,
            @Parameter(description = "Updated booking details") @RequestBody BookingDTO bookingDTO,
            @AuthenticationPrincipal UserDetails userDetails) {
        UserDTO user = userService.findByUsername(userDetails.getUsername());
        BookingDTO updatedBooking = bookingService.updateBooking(id, bookingDTO, user);

        if (updatedBooking != null) {
            return new ResponseEntity<>(updatedBooking, HttpStatus.OK);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @Operation(summary = "Delete a booking by ID")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBooking(
            @Parameter(description = "ID of the booking to be deleted") @PathVariable Long id,
            @AuthenticationPrincipal UserDetails userDetails) {
        UserDTO user = userService.findByUsername(userDetails.getUsername());
        boolean isDeleted = bookingService.deleteBooking(id, user);

        if (isDeleted) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }
}
