package com.flightbookings.flight_bookings.dtos.DTOUser;

import com.flightbookings.flight_bookings.dtos.DTOBooking.BookingDTO;
import com.flightbookings.flight_bookings.models.Booking;
import com.flightbookings.flight_bookings.models.User;
import com.flightbookings.flight_bookings.services.interfaces.BookingService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class UserConverter {

    private final ModelMapper modelMapper;
    private final BookingService bookingService;

    public UserConverter(ModelMapper modelMapper, BookingService bookingService) {
        this.modelMapper = modelMapper;
        this.bookingService = bookingService;
    }

    /**
     * Converts a UserDTO to a User entity, mapping associated bookings if present.
     *
     * @param userDTO the UserDTO to convert
     * @param user    the existing User entity to update (can be null for new users)
     * @return the converted User entity with mapped bookings
     */
    public User dtoToUser(UserDTO userDTO, User user) {
        User mappedUser = user != null ? user : new User();
        modelMapper.map(userDTO, mappedUser);

        if (userDTO.getBookings() != null) {
            List<Booking> bookings = userDTO.getBookings().stream()
                    .map(bookingDTO -> bookingService.getBookingById(bookingDTO.getBookingId(), mappedUser))
                    .filter(booking -> booking != null)
                    .collect(Collectors.toList());
            mappedUser.setBookings(bookings);
        }

        return mappedUser;
    }

    /**
     * Converts a User entity to a UserDTO, mapping associated bookings if present.
     *
     * @param user the User entity to convert
     * @return the converted UserDTO with mapped bookings
     */
    public UserDTO userToDto(User user) {
        UserDTO userDTO = modelMapper.map(user, UserDTO.class);
        if (user.getBookings() != null) {
            List<BookingDTO> bookingDTOs = user.getBookings().stream()
                    .map(booking -> modelMapper.map(booking, BookingDTO.class))
                    .collect(Collectors.toList());
            userDTO.setBookings(bookingDTOs);
        }
        return userDTO;
    }
}