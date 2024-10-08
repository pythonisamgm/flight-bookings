package com.flightbookings.flight_bookings.services;

import com.flightbookings.flight_bookings.exceptions.FlightNotFoundException;
import com.flightbookings.flight_bookings.models.Flight;
import com.flightbookings.flight_bookings.models.EFlightAirplane;
//import com.flightbookings.flight_bookings.repositories.IAirportRepository;
import com.flightbookings.flight_bookings.repositories.IFlightRepository;
import com.flightbookings.flight_bookings.repositories.ISeatRepository;
import com.flightbookings.flight_bookings.services.interfaces.FlightDurationService;
import com.flightbookings.flight_bookings.services.interfaces.FlightService;
import com.flightbookings.flight_bookings.services.interfaces.SeatService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class FlightServiceImpl implements FlightService {

    private final IFlightRepository flightRepository;
    private final ISeatRepository seatRepository;
    private final SeatService seatService;
    //private final IAirportRepository airportRepository;
    private final FlightDurationService flightDurationService;

    public FlightServiceImpl(IFlightRepository flightRepository, ISeatRepository seatRepository, SeatService seatService, FlightDurationService flightDurationService) {
        this.flightRepository = flightRepository;
        this.seatRepository = seatRepository;
        this.seatService = seatService;
        this.flightDurationService = flightDurationService;
    }

    @Override
    @Transactional
    public Flight createFlight(Flight flight) {
        flight.setSeats(new ArrayList<>());
        Flight savedFlight = flightRepository.save(flight);
        int durationMinutes = flightDurationService.calculateFlightDuration(flight);
        flight.setFlightDuration(durationMinutes);
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

            int durationMinutes = flightDurationService.calculateFlightDuration(flightDetails);
            flightDetails.setFlightDuration(durationMinutes);

            return flightRepository.save(flightDetails);
        }
        return null;
    }


    @Override
    public String deleteFlight(Long id) {
        if (!flightRepository.existsById(id)) {
            throw new FlightNotFoundException("Flight with ID " + id + " not found.");
        }

        flightRepository.deleteById(id);
        return "Flight successfully deleted";
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