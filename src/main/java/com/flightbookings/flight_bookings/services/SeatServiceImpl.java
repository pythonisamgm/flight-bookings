package com.flightbookings.flight_bookings.services;

import com.flightbookings.flight_bookings.repositories.ISeatRepository;
import com.flightbookings.flight_bookings.services.interfaces.SeatService;
import org.springframework.stereotype.Service;

@Service
public class SeatServiceImpl implements SeatService {

    private final ISeatRepository seatRepository;

    public SeatServiceImpl(ISeatRepository seatRepository) {
        this.seatRepository = seatRepository;
    }
}
