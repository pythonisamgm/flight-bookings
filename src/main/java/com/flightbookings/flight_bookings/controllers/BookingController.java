package com.flightbookings.flight_bookings.controllers;

import com.flightbookings.flight_bookings.dtos.DTOBooking.BookingConverter;
import com.flightbookings.flight_bookings.dtos.DTOBooking.BookingDTO;
import com.flightbookings.flight_bookings.exceptions.FlightNotFoundException;
import com.flightbookings.flight_bookings.exceptions.PassengerNotFoundException;
import com.flightbookings.flight_bookings.exceptions.SeatNotFoundException;
import com.flightbookings.flight_bookings.exceptions.UserNotFoundException;
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
     * @param bookingConverter the converter for Booking and BookingDTO objects.
     */
    public BookingController(BookingService bookingService, UserService userService, BookingConverter bookingConverter) {
        this.bookingService = bookingService;
        this.userService = userService;
        this.bookingConverter = bookingConverter;
    }

    /**
     * Creates a new booking.
     *
     * @param flightId    the ID of the flight.
     * @param passengerId the ID of the passenger.
     * @param seatName    the name of the seat.
     * @param principal   the principal object to retrieve the current user.
     * @return the created booking.
     */
    @PostMapping(value = "/create/{flightId}/{passengerId}/{seatName}")
    @Operation(summary = "Create booking")
    public ResponseEntity<BookingDTO> createBooking(
            @PathVariable("flightId") Long flightId,
            @PathVariable("passengerId") Long passengerId,
            @PathVariable("seatName") String seatName,
            Principal principal) {

        if (principal == null) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }

        User user = userService.findByUsername(principal.getName());
        Booking booking = bookingService.createBooking(flightId, passengerId, seatName, user.getUserId());

        try {
            BookingDTO bookingDto = bookingConverter.bookingToDto(booking);
            if (bookingDto == null) {
                throw new RuntimeException("Conversion error: BookingDTO is null");
            }
            return new ResponseEntity<>(bookingDto, HttpStatus.CREATED);
        } catch (FlightNotFoundException | PassengerNotFoundException | SeatNotFoundException | UserNotFoundException e) {
            System.out.println("Exception caught in createBooking: " + e.getMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        } catch (Exception e) {
            System.out.println("Unexpected error in createBooking: " + e.getMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Updates an existing booking.
     *
     * @param id             the ID of the booking to be updated.
     * @param updatedBooking the booking object with updated details.
     * @return the updated booking as a BookingDTO, or a 404 status if not found.
     */
    @Operation(summary = "Update existing booking")
    @PutMapping("/{id}")
    public ResponseEntity<BookingDTO> updateBooking(
            @Parameter(description = "ID of the booking to be retrieved") @PathVariable Long id,
            @RequestBody Booking updatedBooking) {

        updatedBooking.setBookingId(id);
        Booking booking = bookingService.updateBooking(updatedBooking);

        if (booking != null) {
            BookingDTO bookingDto = bookingConverter.bookingToDto(booking);
            return new ResponseEntity<>(bookingDto, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    /**
     * Retrieves all bookings for the current user.
     *
     * @param principal the principal object to get the current user.
     * @return the list of bookings for the current user as a list of BookingDTOs.
     */
    @Operation(summary = "Get all bookings for the current user")
    @GetMapping("/")
    public ResponseEntity<List<BookingDTO>> getAllBookingsByUser(Principal principal) {
        User user = userService.findByUsername(principal.getName());
        List<Booking> bookings = bookingService.getAllBookingsByUser(user);
        List<BookingDTO> bookingDtos = bookingConverter.bookingsToDtoList(bookings);
        return new ResponseEntity<>(bookingDtos, HttpStatus.OK);
    }
    @GetMapping("/{id}")
    public ResponseEntity<BookingDTO> getBookingById(
            @Parameter(description = "ID of the booking to be retrieved") @PathVariable Long id,
            Principal principal) {

        User user = userService.findByUsername(principal.getName());
        Booking booking = bookingService.getBookingById(id, user);

        if (booking != null) {
            BookingDTO bookingDto = bookingConverter.bookingToDto(booking);
            return new ResponseEntity<>(bookingDto, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
    @Operation(summary = "Get all bookings")
    @GetMapping("/all")
    public ResponseEntity<List<BookingDTO>> getAllBookings() {
        List<Booking> bookings = bookingService.getAllBookings();
        List<BookingDTO> bookingDtos = bookingConverter.bookingsToDtoList(bookings);
        return new ResponseEntity<>(bookingDtos, HttpStatus.OK);
    }

    /**
     * Deletes an existing booking by ID.
     *
     * @param id the ID of the booking to be deleted.
     * @return a 204 response if deleted, otherwise 404 if not found.
     */
    @Operation(summary = "Delete existing booking by ID")
    @DeleteMapping("/delete/{id}")
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
