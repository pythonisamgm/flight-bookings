package com.flightbookings.flight_bookings.services;

import com.flightbookings.flight_bookings.repositories.IAirportRepository;
import com.flightbookings.flight_bookings.services.interfaces.UserService;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    private final IAirportRepository airportRepository;

    public UserServiceImpl(IAirportRepository airportRepository) {
        this.airportRepository = airportRepository;
    }
}
