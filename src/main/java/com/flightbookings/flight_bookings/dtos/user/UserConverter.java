package com.flightbookings.flight_bookings.dtos.user;
import com.flightbookings.flight_bookings.models.ERole;
import com.flightbookings.flight_bookings.models.User;

public class UserConverter {

    public UserDTO userToDto(User user) {
        UserDTO userDTO = new UserDTO();
        userDTO.setUserId(user.getId());
        userDTO.setUsername(user.getUsername());
        userDTO.setEmail(user.getEmail());
        userDTO.setRole(user.getRole().name());
        return userDTO;
    }

    public User dtoToUser(UserDTO userDTO) {
        User user = new User();
        user.setId(userDTO.getUserId());
        user.setUsername(userDTO.getUsername());
        user.setEmail(userDTO.getEmail());
        user.setRole(ERole.valueOf(userDTO.getRole()));
        return user;
    }
}

