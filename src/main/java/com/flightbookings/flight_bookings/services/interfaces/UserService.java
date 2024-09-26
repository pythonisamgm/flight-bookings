package com.flightbookings.flight_bookings.services.interfaces;

import com.flightbookings.flight_bookings.models.User;

import java.util.List;

public interface UserService {
    User createUser(User user);

    User getUserById(Long id);

    List<User> getAllUsers();

    User updateUser(Long id, User userDetails);

    boolean deleteUser(Long id);
}
