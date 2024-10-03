package com.flightbookings.flight_bookings.dtos.DTOUser;

import com.flightbookings.flight_bookings.models.Booking;
import com.flightbookings.flight_bookings.models.ERole;
import com.flightbookings.flight_bookings.models.User;
import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;
import org.modelmapper.Converter;
import org.springframework.stereotype.Component;

@Component
public class UserConverter {

    private final ModelMapper modelMapper;

    public UserConverter(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
        //configure();
    }

//    private void configure() {
//        modelMapper.addMappings(new PropertyMap<User, UserDTO>() {
//            @Override
//            protected void configure() {
//                using(roleToStringConverter()).map(source.getRole(), destination.getRole());
//                map().setBookingIds(source.getBookings() != null
//                        ? source.getBookings().stream().map(Booking::getBookingId).toList()
//                        : null);
//            }
//        });
//
//        modelMapper.addMappings(new PropertyMap<UserDTO, User>() {
//            @Override
//            protected void configure() {
//                using(stringToRoleConverter()).map(source.getRole()).setRole(null);
//            }
//        });
//    }
//
//    private Converter<ERole, String> roleToStringConverter() {
//        return context -> context.getSource() != null ? context.getSource().name() : null;
//    }
//
//    private Converter<String, ERole> stringToRoleConverter() {
//        return context -> context.getSource() != null ? ERole.valueOf(context.getSource()) : null;
//    }

    public UserDTO convertToDto(User user) {
        return modelMapper.map(user, UserDTO.class);
    }

    public User convertToEntity(UserDTO userDTO) {
        return modelMapper.map(userDTO, User.class);
    }
}
