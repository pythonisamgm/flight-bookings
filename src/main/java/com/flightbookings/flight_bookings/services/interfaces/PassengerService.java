package com.flightbookings.flight_bookings.services.interfaces;

import com.flightbookings.flight_bookings.dtos.DTOPassenger.PassengerDTO;
import com.flightbookings.flight_bookings.models.Passenger;

import java.util.List;

public interface PassengerService {

    PassengerDTO createPassenger(PassengerDTO passengerDTO);

    PassengerDTO getPassengerById(Long id);

    List<PassengerDTO> getAllPassengers();

    PassengerDTO updatePassenger(Long id, PassengerDTO passengerDetails);

    boolean deletePassenger(Long id);
}
