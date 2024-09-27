package com.flightbookings.flight_bookings.services.interfaces;

import com.flightbookings.flight_bookings.models.Flight;
import com.flightbookings.flight_bookings.repositories.IFlightRepository;
import com.flightbookings.flight_bookings.repositories.ISeatRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface FlightService {


    @Transactional
    Flight createFlight(Flight flight);

    Flight getFlightById(Long id);

    List<Flight> getAllFlights();

    Flight updateFlight(Long id, Flight flightDetails);

    boolean deleteFlight(Long id);
}
