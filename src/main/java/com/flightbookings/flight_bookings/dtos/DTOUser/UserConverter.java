package com.flightbookings.flight_bookings.dtos.DTOUser;


import com.flightbookings.flight_bookings.models.Booking;
import com.flightbookings.flight_bookings.models.ERole;
import com.flightbookings.flight_bookings.models.User;

import java.util.stream.Collectors;

public class UserConverter {

    public static UserDTO toDTO(User user) {
        if (user == null) {
            return null;
        }
        UserDTO dto = new UserDTO();
        dto.setUserId(user.getUserId());
        dto.setUsername(user.getUsername());
        dto.setEmail(user.getEmail());
        dto.setRole(user.getRole().name());
        dto.setBookingIds(user.getBookings().stream().map(Booking::getBookingId).collect(Collectors.toList()));
        return dto;
    }

    public static User toEntity(UserDTO dto) {
        if (dto == null) {
            return null;
        }
        User user = new User();
        user.setUserId(dto.getUserId());
        user.setUsername(dto.getUsername());
        user.setEmail(dto.getEmail());
        user.setPassword("");
        user.setRole(ERole.valueOf(dto.getRole()));
        return user;
    }
}

