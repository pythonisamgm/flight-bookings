package com.flightbookings.flight_bookings.services;

import com.flightbookings.flight_bookings.exceptions.UserNotFoundException;
import com.flightbookings.flight_bookings.models.User;
import com.flightbookings.flight_bookings.repositories.IUserRepository;
import com.flightbookings.flight_bookings.services.interfaces.UserService;
import org.springframework.stereotype.Service;

import java.util.List;
/**
 * Implementation of the UserService interface for managing user operations.
 */
@Service
public class UserServiceImpl implements UserService {

    private final IUserRepository userRepository;
    /**
     * Constructs a UserServiceImpl with the required user repository.
     *
     * @param userRepository the repository for managing users.
     */
    public UserServiceImpl(IUserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public User createUser(User user) {
        return userRepository.save(user);
    }

    @Override
    public User getUserById(Long id) {
        return userRepository.findById(id).orElse(null);
    }

    @Override
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    @Override
    public User updateUser(Long id, User userDetails) {
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
    public User findByUsername(String username) {
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new UserNotFoundException("User not found with username: " + username));
    }
}
