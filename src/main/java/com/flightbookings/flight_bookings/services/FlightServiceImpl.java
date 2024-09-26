package com.flightbookings.flight_bookings.services;

import com.flightbookings.flight_bookings.repositories.IFlightRepository;
import com.flightbookings.flight_bookings.repositories.ISeatRepository;
import com.flightbookings.flight_bookings.services.interfaces.FlightService;
import org.springframework.stereotype.Service;

@Service
public class FlightServiceImpl implements FlightService {

    private final IFlightRepository flightRepository;
    private final ISeatRepository seatRepository;

    public FlightServiceImpl(IFlightRepository flightRepository, ISeatRepository seatRepository) {
        this.flightRepository = flightRepository;
        this.seatRepository = seatRepository;

    }

}
