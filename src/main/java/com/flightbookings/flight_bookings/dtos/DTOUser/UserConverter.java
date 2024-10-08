package com.flightbookings.flight_bookings.dtos.DTOUser;

import com.flightbookings.flight_bookings.models.User;
import org.modelmapper.ModelMapper;


public class UserConverter {

    private final ModelMapper modelMapper;

    public UserConverter(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    public UserDTO convertToDto(User user) {
        return modelMapper.map(user, UserDTO.class);
    }

    public User convertToEntity(UserDTO userDTO) {
        return modelMapper.map(userDTO, User.class);
    }
}
