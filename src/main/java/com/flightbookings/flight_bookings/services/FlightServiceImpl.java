package com.flightbookings.flight_bookings.services;

import com.flightbookings.flight_bookings.repositories.IFlightRepository;
import org.springframework.stereotype.Service;

@Service
public class FlightServiceImpl {

    private final IFlightRepository flightRepository;

    public FlightServiceImpl(IFlightRepository flightRepository){this.flightRepository = flightRepository;}

}
