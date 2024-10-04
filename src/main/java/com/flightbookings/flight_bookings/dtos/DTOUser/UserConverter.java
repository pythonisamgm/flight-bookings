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

    public User dtoToUser(UserDTO userDTO, User user) {
        User mappedUser = modelMapper.map(userDTO, User.class);
        if (userDTO.getBookings() != null) {
            List<Booking> bookings = userDTO.getBookings().stream()
                    .map(bookingDTO -> bookingService.getBookingById(bookingDTO.getBookingId(), user))
                    .filter(booking -> booking != null)
                    .collect(Collectors.toList());
            mappedUser.setBookings(bookings);
        }
        return mappedUser;
    }

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
