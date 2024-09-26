package com.flightbookings.flight_bookings.services.interfaces;

import com.flightbookings.flight_bookings.models.Passenger;

import java.util.List;

public interface PassengerService {
    Passenger createPassenger(Passenger passenger);

    Passenger getPassengerById(Long id);

    List<Passenger> getAllPassengers();

    Passenger updatePassenger(Long id, Passenger passengerDetails);

    boolean deletePassenger(Long id);
}
