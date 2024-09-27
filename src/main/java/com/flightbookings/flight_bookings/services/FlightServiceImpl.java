package com.flightbookings.flight_bookings.services;

import com.flightbookings.flight_bookings.models.Flight;
import com.flightbookings.flight_bookings.repositories.IFlightRepository;
import com.flightbookings.flight_bookings.repositories.ISeatRepository;
import com.flightbookings.flight_bookings.services.interfaces.FlightService;
import com.flightbookings.flight_bookings.services.interfaces.SeatService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;


@Service
public class FlightServiceImpl implements FlightService {

    private final IFlightRepository flightRepository;
    private final ISeatRepository seatRepository;
    private final SeatService seatService;

    public FlightServiceImpl(IFlightRepository flightRepository, ISeatRepository seatRepository, SeatService seatService) {
        this.flightRepository = flightRepository;
        this.seatRepository = seatRepository;
        this.seatService = seatService;

    }

    @Override
    @Transactional
    public Flight createFlight(Flight flight) {
        flight.setSeats(new ArrayList<>());
        Flight savedFlight = flightRepository.save(flight);

        List<String> seatIdentifiers = seatService.initializeSeats(savedFlight, flight.getNumRows());

        savedFlight.setSeats(seatRepository.findByFlight(savedFlight));
        return savedFlight;
    }



    @Override
    public Flight getFlightById(Long id) {
        return flightRepository.findById(id).orElse(null);
    }

    @Override
    public List<Flight> getAllFlights() {
        return flightRepository.findAll();
    }

    @Override
    public Flight updateFlight(Long id, Flight flightDetails) {
        if (flightRepository.existsById(id)) {
            flightDetails.setFlightId(id);
            return flightRepository.save(flightDetails);
        }
        return null;
    }

    @Override
    public boolean deleteFlight(Long id) {
        if (flightRepository.existsById(id)) {
            flightRepository.deleteById(id);
            return true;
        }
        return false;
    }
}
