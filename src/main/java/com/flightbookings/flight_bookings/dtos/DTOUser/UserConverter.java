package com.flightbookings.flight_bookings.dtos.DTOUser;


import com.flightbookings.flight_bookings.models.Booking;
import com.flightbookings.flight_bookings.models.User;
import com.flightbookings.flight_bookings.services.interfaces.BookingService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

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
        if (userDTO.getBookingIds() != null) {
            List<Booking> bookings = new ArrayList<>();
            for (Long bookingId : userDTO.getBookingIds()) {
                Booking booking = bookingService.getBookingById(bookingId, user);
                if (booking != null) {
                    bookings.add(booking);
                }
            }
            mappedUser.setBookings(bookings);
        }
        return mappedUser;
    }

    public UserDTO userToDto(User user) {
        UserDTO userDTO = modelMapper.map(user, UserDTO.class);
        return userDTO;
    }
}
