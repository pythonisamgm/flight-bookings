package com.flightbookings.flight_bookings.listeners;


import com.flightbookings.flight_bookings.models.Flight;
import com.flightbookings.flight_bookings.services.SeatServiceImpl;
import jakarta.persistence.PostPersist;
import org.springframework.stereotype.Component;

@Component
public class FlightEntityListener {
    private static SeatServiceImpl seatService;

    public void setSeatService(SeatServiceImpl seatService) {
        FlightEntityListener.seatService = seatService;
    }


    @PostPersist
    public void initializeSeats(Flight flight) {
        seatService.initializeSeats(flight, flight.getNumRows());
    }
}

