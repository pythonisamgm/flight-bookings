package com.flightbookings.flight_bookings.services;

import com.flightbookings.flight_bookings.models.ERole;
import com.flightbookings.flight_bookings.models.UserEntity;
import com.flightbookings.flight_bookings.repositories.IUserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class UserEntityServiceImplTest {

    @Mock
    private IUserRepository userRepository;

    @InjectMocks
    private UserServiceImpl userService;

    private UserEntity user1;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);

        user1 = UserEntity.builder()
                .userId(1L)
                .username("juanantonio")
                .password("1234")
                .email("jantonio@gmail.com")
                .role(ERole.ADMIN)
                .bookings(new ArrayList<>())
                .build();
    }

    @Test
    public void testCreateUser() {
        when(userRepository.save(any(UserEntity.class))).thenReturn(user1);

        UserEntity createdUser = userService.createUser(user1);

        assertNotNull(createdUser);
        assertEquals("juanantonio", createdUser.getUsername());
        verify(userRepository, times(1)).save(any(UserEntity.class));
    }

    @Test
    public void testGetUserById() {
        when(userRepository.findById(1L)).thenReturn(Optional.of(user1));

        UserEntity foundUser = userService.getUserById(1L);

        assertNotNull(foundUser);
        assertEquals("juanantonio", foundUser.getUsername());
        verify(userRepository, times(1)).findById(1L);
    }

    @Test
    public void testUpdateUser() {
        when(userRepository.existsById(1L)).thenReturn(true);
        when(userRepository.save(any(UserEntity.class))).thenReturn(user1);

        UserEntity updatedUser = userService.updateUser(1L, user1);

        assertNotNull(updatedUser);
        assertEquals("juanantonio", updatedUser.getUsername());
        verify(userRepository, times(1)).save(any(UserEntity.class));
    }

    @Test
    public void testDeleteUser() {
        when(userRepository.existsById(1L)).thenReturn(true);

        boolean isDeleted = userService.deleteUser(1L);

        assertTrue(isDeleted);
        verify(userRepository, times(1)).deleteById(1L);
    }

    @Test
    public void testDeleteUser_NotFound() {
        when(userRepository.existsById(1L)).thenReturn(false);

        boolean isDeleted = userService.deleteUser(1L);

        assertFalse(isDeleted);
        verify(userRepository, times(0)).deleteById(1L);
    }
}