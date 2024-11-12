package com.flightbookings.flight_bookings.dtos.DTOUser;

import com.flightbookings.flight_bookings.models.UserEntity;
import org.modelmapper.ModelMapper;


public class UserConverter {

    private final ModelMapper modelMapper;

    public UserConverter(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    public UserDTO convertToDto(UserEntity user) {
        return modelMapper.map(user, UserDTO.class);
    }

    public UserEntity convertToEntity(UserDTO userDTO) {
        return modelMapper.map(userDTO, UserEntity.class);
    }
}
