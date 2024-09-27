package com.flightbookings.flight_bookings.services;

import com.flightbookings.flight_bookings.models.Flight;
import com.flightbookings.flight_bookings.models.EFlightAirplane;
import com.flightbookings.flight_bookings.repositories.IAirportRepository;
import com.flightbookings.flight_bookings.repositories.IFlightRepository;
import com.flightbookings.flight_bookings.repositories.ISeatRepository;
import com.flightbookings.flight_bookings.services.interfaces.FlightService;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class FlightServiceImpl implements FlightService {

    private final IFlightRepository flightRepository;
    private final ISeatRepository seatRepository;
    private final IAirportRepository airportRepository;

    public FlightServiceImpl(IFlightRepository flightRepository, ISeatRepository seatRepository, IAirportRepository airportRepository) {
        this.flightRepository = flightRepository;
        this.seatRepository = seatRepository;
        this.airportRepository = airportRepository;
    }

    @Override
    public Flight createFlight(Flight flight) {
        return flightRepository.save(flight);
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

//    public List<Flight> searchFlightsByCity(String city) {
//        return flightRepository.findAll()
//                .stream()
//                .filter(flight -> flight.getAirports().stream().anyMatch(airport -> airport.getCity().equalsIgnoreCase(city)))
//                .collect(Collectors.toList());
//    }

    public void cancelFlight(Long id) {
        Flight flight = getFlightById(id);
        if (flight != null) {
            flight.setAvailability(false);
            flightRepository.save(flight);
        }
    }

    public void delayFlight(Long id, LocalDateTime newDepartureTime) {
        Flight flight = getFlightById(id);
        if (flight != null) {
            flight.setDepartureTime(newDepartureTime);
            flightRepository.save(flight);
        }
    }

    public List<Flight> getFlightsByAirplaneType(EFlightAirplane airplaneType) {
        return flightRepository.findAll()
                .stream()
                .filter(flight -> flight.getFlightAirplane() == airplaneType)
                .collect(Collectors.toList());
    }

    public void updateFlightAvailability() {
        LocalDateTime now = LocalDateTime.now();
        List<Flight> flights = flightRepository.findAll();
        for (Flight flight : flights) {
            if (flight.getArrivalTime().isBefore(now) || flight.getSeats().isEmpty()) {
                flight.setAvailability(false);
                flightRepository.save(flight);
            }
        }
    }
}
