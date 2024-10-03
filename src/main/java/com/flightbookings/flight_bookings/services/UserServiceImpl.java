package com.flightbookings.flight_bookings.services;

import com.flightbookings.flight_bookings.dtos.DTOUser.UserConverter;
import com.flightbookings.flight_bookings.dtos.DTOUser.UserDTO;
import com.flightbookings.flight_bookings.exceptions.UserNotFoundException;
import com.flightbookings.flight_bookings.models.User;
import com.flightbookings.flight_bookings.repositories.IUserRepository;
import com.flightbookings.flight_bookings.services.interfaces.UserService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {

    private final IUserRepository userRepository;
    private final UserConverter userConverter;

    public UserServiceImpl(IUserRepository userRepository, UserConverter userConverter) {
        this.userRepository = userRepository;
        this.userConverter = userConverter;
    }

    @Override
    public UserDTO createUser(UserDTO userDTO) {
        User user = userConverter.convertToEntity(userDTO);
        User savedUser = userRepository.save(user);
        return userConverter.convertToDto(savedUser);
    }

    @Override
    public UserDTO getUserById(Long id) {
        return userRepository.findById(id)
                .map(userConverter::convertToDto)
                .orElseThrow(() -> new UserNotFoundException("User not found with id: " + id));
    }

    @Override
    public List<UserDTO> getAllUsers() {
        return userRepository.findAll().stream()
                .map(userConverter::convertToDto)
                .collect(Collectors.toList());
    }

    @Override
    public UserDTO updateUser(Long id, UserDTO userDTO) {
        if (userRepository.existsById(id)) {
            User userToUpdate = userConverter.convertToEntity(userDTO);
            userToUpdate.setUserId(id);
            User updatedUser = userRepository.save(userToUpdate);
            return userConverter.convertToDto(updatedUser);
        }
        throw new UserNotFoundException("User not found with id: " + id);
    }

    @Override
    public boolean deleteUser(Long id) {
        if (userRepository.existsById(id)) {
            userRepository.deleteById(id);
            return true;
        }
        throw new UserNotFoundException("User not found with id: " + id);
    }

    @Override
    public UserDTO findByUsername(String username) {
        return userRepository.findByUsername(username)
                .map(userConverter::convertToDto)
                .orElseThrow(() -> new UserNotFoundException("User not found with username: " + username));
    }
}
