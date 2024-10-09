package com.flightbookings.flight_bookings.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.flightbookings.flight_bookings.dtos.DTOUser.UserDTO;
import com.flightbookings.flight_bookings.dtos.DTOUser.UserConverter;
import com.flightbookings.flight_bookings.models.ERole;
import com.flightbookings.flight_bookings.models.User;
import com.flightbookings.flight_bookings.services.interfaces.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class UserControllerTest {

    @Mock
    private UserService userService;

    @Mock
    private UserConverter userConverter;

    @InjectMocks
    private UserController userController;

    private MockMvc mockMvc;
    private User user;
    private UserDTO userDTO;
    private UserDTO userDTO2;
    private List<UserDTO> userDTOList;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(userController).build();

        user = new User();
        user.setUserId(1L);
        user.setUsername("juanantonio");
        user.setEmail("jantonio@gmail.com");
        user.setRole(ERole.ADMIN);

        userDTO = new UserDTO();
        userDTO.setUserId(1L);
        userDTO.setUsername("juanantonio");
        userDTO.setEmail("jantonio@gmail.com");
        userDTO.setRole("ADMIN");

        userDTO2 = new UserDTO();
        userDTO2.setUserId(2L);
        userDTO2.setUsername("miguelangel");
        userDTO2.setEmail("mangel@gmail.com");
        userDTO2.setRole("USER");

        userDTOList = new ArrayList<>();
        userDTOList.add(userDTO);
        userDTOList.add(userDTO2);
    }

    @Test
    public void testCreateUser() throws Exception {
        when(userConverter.dtoToUser(any(UserDTO.class), eq(null))).thenReturn(user);
        when(userService.createUser(any(User.class))).thenReturn(user);
        when(userConverter.userToDto(any(User.class))).thenReturn(userDTO);

        mockMvc.perform(post("/api/v1/user/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(userDTO)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.userId").value(1L))
                .andExpect(jsonPath("$.username").value("juanantonio"))
                .andExpect(jsonPath("$.email").value("jantonio@gmail.com"))
                .andExpect(jsonPath("$.role").value("ADMIN"));

        verify(userService, times(1)).createUser(any(User.class));
    }

    @Test
    public void testGetUserById() throws Exception {
        when(userService.getUserById(1L)).thenReturn(user);
        when(userConverter.userToDto(user)).thenReturn(userDTO);

        mockMvc.perform(get("/api/v1/user/{id}", 1L))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.userId").value(1L))
                .andExpect(jsonPath("$.username").value("juanantonio"));

        verify(userService, times(1)).getUserById(1L);
    }

    @Test
    public void testGetAllUsers() throws Exception {
        List<User> users = List.of(user);
        when(userService.getAllUsers()).thenReturn(users);
        when(userConverter.userToDto(user)).thenReturn(userDTO);
        when(userConverter.userToDto(users.get(0))).thenReturn(userDTO);

        mockMvc.perform(get("/api/v1/user/"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].userId").value(1L))
                .andExpect(jsonPath("$[0].username").value("juanantonio"));

        verify(userService, times(1)).getAllUsers();
    }

    @Test
    public void testUpdateUser() throws Exception {
        when(userService.getUserById(1L)).thenReturn(user);
        when(userConverter.dtoToUser(any(UserDTO.class), any(User.class))).thenReturn(user);
        when(userService.updateUser(eq(1L), any(User.class))).thenReturn(user);
        when(userConverter.userToDto(user)).thenReturn(userDTO);

        mockMvc.perform(put("/api/v1/user/update/{id}", 1L)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(userDTO)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.userId").value(1L))
                .andExpect(jsonPath("$.username").value("juanantonio"));

        verify(userService, times(1)).updateUser(eq(1L), any(User.class));
    }

    @Test
    public void testDeleteUser() throws Exception {
        when(userService.deleteUser(1L)).thenReturn(true);

        mockMvc.perform(delete("/api/v1/user/delete/{id}", 1L))
                .andExpect(status().isNoContent());

        verify(userService, times(1)).deleteUser(1L);
    }
}
