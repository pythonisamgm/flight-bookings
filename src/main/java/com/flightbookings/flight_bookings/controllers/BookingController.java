package com.flightbookings.flight_bookings.controllers;

import com.flightbookings.flight_bookings.dtos.DTOBooking.BookingConverter;
import com.flightbookings.flight_bookings.dtos.DTOBooking.BookingDTO;
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
    private final BookingConverter bookingConverter; // Add BookingConverter

    /**
     * Constructor to initialize the BookingController with BookingService and UserService.
     *
     * @param bookingService  the service for booking management.
     * @param userService     the service for user management.
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
     * This endpoint allows a user to create a booking by specifying the flight ID, passenger ID,
     * seat name, and user ID. The user is authenticated via the authentication object.
     *
     * @param flightId      the ID of the flight to be booked.
     * @param passengerId   the ID of the passenger.
     * @param seatName      the name of the seat.
     * @param userId        the ID of the user making the booking.
     * @param authentication the authentication object to retrieve the current user.
     * @return ResponseEntity containing the created BookingDTO and an HTTP status of 201 (Created).
     */
    @Operation(summary = "Create a new booking")
    @PostMapping(value = "/create/{flightId}/{passengerId}/{seatName}/{userId}")
    public ResponseEntity<BookingDTO> createBooking(@PathVariable("flightId") Long flightId,
                                                    @PathVariable("passengerId") Long passengerId,
                                                    @PathVariable("seatName") String seatName,
                                                    @PathVariable("userId") Long userId,
                                                    @AuthenticationPrincipal Authentication authentication) {
        User user = userService.findByUsername(authentication.getName());
        Booking booking = bookingService.createBooking(flightId, passengerId, seatName, userId);
        BookingDTO bookingDTO = bookingConverter.bookingToDto(booking);
        return new ResponseEntity<>(bookingDTO, HttpStatus.CREATED);
    }

    /**
     * Updates an existing booking.
     *
     * This endpoint allows the user to update an existing booking by providing the booking ID
     * and the new details in the BookingDTO.
     *
     * @param id             the ID of the booking to be updated.
     * @param updatedBooking the BookingDTO with updated details.
     * @return ResponseEntity containing the updated BookingDTO and an HTTP status of 200 (OK),
     *         or 404 (Not Found) if the booking does not exist.
     */
    @Operation(summary = "Update existing booking")
    @PutMapping("/{id}")
    public ResponseEntity<BookingDTO> updateBooking(@Parameter(description = "ID of the booking to be retrieved") @PathVariable Long id,
                                                    @RequestBody BookingDTO updatedBooking) {
        Booking booking = bookingConverter.dtoToBooking(updatedBooking);
        booking.setBookingId(id);
        Booking updatedBookingEntity = bookingService.updateBooking(booking);
        BookingDTO bookingDTO = bookingConverter.bookingToDto(updatedBookingEntity);
        return new ResponseEntity<>(bookingDTO, HttpStatus.OK);
    }

    /**
     * Creates a new booking using an alternative method.
     *
     * This endpoint allows the user to create a booking by providing a BookingDTO object.
     *
     * @param bookingDTO the BookingDTO containing details of the booking to be created.
     * @return ResponseEntity containing the created BookingDTO and an HTTP status of 201 (Created).
     */
    @Operation(summary = "Create a new booking. Version 1")
    @PostMapping(value = "/create2", consumes = "application/json")
    public ResponseEntity<BookingDTO> createBooking2(@RequestBody BookingDTO bookingDTO) {
        Booking booking = bookingConverter.dtoToBooking(bookingDTO);
        Booking newBooking = bookingService.createBooking2(booking);
        BookingDTO newBookingDTO = bookingConverter.bookingToDto(newBooking);
        return new ResponseEntity<>(newBookingDTO, HttpStatus.CREATED);
    }

    /**
     * Retrieves a booking by its ID.
     *
     * This endpoint allows the user to fetch the details of a specific booking by its unique ID.
     *
     * @param id        the ID of the booking to be retrieved.
     * @param principal the principal object to get the current user.
     * @return ResponseEntity containing the BookingDTO if found, otherwise an HTTP status of 404 (Not Found).
     */
    @Operation(summary = "Get booking by ID")
    @GetMapping("/{id}")
    public ResponseEntity<BookingDTO> getBookingById(@Parameter(description = "ID of the booking to be retrieved") @PathVariable Long id, Principal principal) {
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
     * This endpoint allows the user to fetch all bookings associated with their account.
     *
     * @param principal the principal object to get the current user.
     * @return ResponseEntity containing a list of BookingDTOs and an HTTP status of 200 (OK).
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
     * Retrieves all bookings in the system.
     *
     * This endpoint allows the admin or authorized user to fetch all bookings present in the system.
     *
     * @return ResponseEntity containing a list of BookingDTOs and an HTTP status of 200 (OK).
     */
    @Operation(summary = "Get all bookings")
    @GetMapping("/all")
    public ResponseEntity<List<BookingDTO>> getAllBookings() {
        List<Booking> bookings = bookingService.getAllBookings();
        List<BookingDTO> bookingDTOs = bookingConverter.bookingsToDtoList(bookings);
        return new ResponseEntity<>(bookingDTOs, HttpStatus.OK);
    }

    /**
     * Updates an existing booking using an alternative method.
     *
     * This endpoint allows the user to update an existing booking by providing the booking ID
     * and the new details in the BookingDTO.
     *
     * @param id             the ID of the booking to be updated.
     * @param bookingDetails the BookingDTO with updated details.
     * @return ResponseEntity containing the updated BookingDTO and an HTTP status of 200 (OK),
     *         or 404 (Not Found) if the booking does not exist.
     */
    @Operation(summary = "Update an existing booking-Version 1")
    @PutMapping("/update/{id}")
    public ResponseEntity<BookingDTO> updateBooking2(@Parameter(description = "ID of the booking to be retrieved") @PathVariable Long id,
                                                     @RequestBody BookingDTO bookingDetails) {
        Booking booking = bookingConverter.dtoToBooking(bookingDetails);
        Booking updatedBooking = bookingService.updateBooking2(id, booking);
        if (updatedBooking != null) {
            BookingDTO bookingDTO = bookingConverter.bookingToDto(updatedBooking);
            return new ResponseEntity<>(bookingDTO, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    /**
     * Deletes an existing booking by ID.
     *
     * This endpoint allows the user to delete a specific booking from the system using its unique ID.
     *
     * @param id the ID of the booking to be deleted.
     * @return ResponseEntity with HTTP status of 204 (No Content) if deletion is successful,
     *         otherwise an HTTP status of 404 (Not Found).
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