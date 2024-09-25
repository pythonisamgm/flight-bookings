package com.flightbookings.flight_bookings.services;

import com.flightbookings.flight_bookings.repositories.IPassengerRepository;
import com.flightbookings.flight_bookings.services.interfaces.PassengerService;
import org.springframework.stereotype.Service;

@Service
public class PassengerServiceImpl implements PassengerService {

    private final IPassengerRepository passengerRepository;

    public PassengerServiceImpl(IPassengerRepository passengerRepository) {
        this.passengerRepository = passengerRepository;
    }

}
