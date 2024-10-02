package com.flightbookings.flight_bookings.dtos.DTOPassenger;

import com.flightbookings.flight_bookings.models.Passenger;
import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;

public class PassengerConverter {

    private final ModelMapper modelMapper;

    public PassengerConverter(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
        configure();
    }

    private void configure() {
        modelMapper.addMappings(new PropertyMap<Passenger, PassengerDTO>() {
            @Override
            protected void configure() {
                map().setBookingId(source.getBooking() != null ? source.getBooking().getBookingId() : null);
            }
        });

        modelMapper.addMappings(new PropertyMap<PassengerDTO, Passenger>() {
            @Override
            protected void configure() {
            }
        });
    }

    public PassengerDTO convertToDto(Passenger passenger) {
        return modelMapper.map(passenger, PassengerDTO.class);
    }

    public Passenger convertToEntity(PassengerDTO passengerDTO) {
        return modelMapper.map(passengerDTO, Passenger.class);
    }
}
