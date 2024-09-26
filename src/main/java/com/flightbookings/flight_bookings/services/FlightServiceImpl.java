package com.flightbookings.flight_bookings.services;

import com.flightbookings.flight_bookings.models.Flight;
import com.flightbookings.flight_bookings.models.User;
import com.flightbookings.flight_bookings.repositories.IFlightRepository;
import com.flightbookings.flight_bookings.repositories.ISeatRepository;
import com.flightbookings.flight_bookings.services.interfaces.FlightService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FlightServiceImpl implements FlightService {

    private final IFlightRepository flightRepository;
    private final ISeatRepository seatRepository;

    public FlightServiceImpl(IFlightRepository flightRepository, ISeatRepository seatRepository) {
        this.flightRepository = flightRepository;
        this.seatRepository = seatRepository;

    }
    public Flight createFlight(Flight flight) {
        return flightRepository.save(flight);
    }

    public Flight getFlightById(Long id) {
        return flightRepository.findById(id).orElse(null);
    }

    public List<Flight> getAllFlights()  {
        return flightRepository.findAll();
    }

    public Flight updateFlight(Long id, Flight flightDetails) {
        if (flightRepository.existsById(id)) {
            flightDetails.setId(id);
            return flightRepository.save(flightDetails);
        }
        return null;
    }

    public boolean deleteFlight(Long id) {
        if (flightRepository.existsById(id)) {
            flightRepository.deleteById(id);
            return true;
        }
        return false;
    }
}
