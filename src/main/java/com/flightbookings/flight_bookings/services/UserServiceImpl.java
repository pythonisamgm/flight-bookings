package com.flightbookings.flight_bookings.services;

import com.flightbookings.flight_bookings.exceptions.UserNotFoundException;
import com.flightbookings.flight_bookings.models.UserEntity;
import com.flightbookings.flight_bookings.repositories.IUserRepository;
import com.flightbookings.flight_bookings.services.interfaces.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
/**
 * Implementation of the UserService interface for managing user operations.
 */
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final IUserRepository userRepository;


    @Override
    public UserEntity createUser(UserEntity user) {
        return userRepository.save(user);
    }

    @Override
    public UserEntity getUserById(Long id) {
        return userRepository.findById(id).orElse(null);
    }

    @Override
    public List<UserEntity> getAllUsers() {
        return userRepository.findAll();
    }

    @Override
    public UserEntity updateUser(Long id, UserEntity userDetails) {
        if (userRepository.existsById(id)) {
            userDetails.setUserId(id);
            return userRepository.save(userDetails);
        }
        return null;
    }

    @Override
    public boolean deleteUser(Long id) {
        if (userRepository.existsById(id)) {
            userRepository.deleteById(id);
            return true;
        }
        return false;
    }

    @Override
    public UserEntity findByUsername(String username) {
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new UserNotFoundException("User not found with username: " + username));
    }
}
