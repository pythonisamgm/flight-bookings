package com.flightbookings.flight_bookings.services;

import com.flightbookings.flight_bookings.models.Flight;
import com.flightbookings.flight_bookings.models.EFlightAirplane;
import com.flightbookings.flight_bookings.models.Seat;
import com.flightbookings.flight_bookings.repositories.IAirportRepository;
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
/**
 * Service implementation for managing flight-related operations.
 */
@Service
public class FlightServiceImpl implements FlightService {

    private final IFlightRepository flightRepository;
    private final FlightDurationService flightDurationService;
    private final SeatService seatService;
    private final ISeatRepository seatRepository;


    public FlightServiceImpl(IFlightRepository flightRepository, FlightDurationService flightDurationService, SeatService seatService, ISeatRepository seatRepository) {
        this.flightRepository = flightRepository;
        this.flightDurationService = flightDurationService;
        this.seatService = seatService;
        this.seatRepository = seatRepository;
    }
    /**
     * Creates a new flight and initializes its seats.
     *
     * @param flight the Flight object to be created
     * @return the saved Flight object with initialized seats
     */
    @Override
    @Transactional
    public Flight createFlight(Flight flight) {
        flight.setFlightDuration(flightDurationService.calculateFlightDuration(flight));
        flight.setSeats(new ArrayList<>());
        Flight savedFlight = flightRepository.save(flight);
        List<String> seatIdentifiers = seatService.initializeSeats(savedFlight, flight.getNumRows());
        savedFlight.setSeats(seatRepository.findByFlight(savedFlight));
        return savedFlight;
    }
    /**
     * Retrieves a flight by its ID.
     *
     * @param id the ID of the flight to retrieve
     * @return the Flight object if found, otherwise null
     */
    @Override
    public Flight getFlightById(Long id) {
        return flightRepository.findById(id).orElse(null);
    }
    /**
     * Retrieves all flights from the database.
     *
     * @return a list of all Flight objects
     */
    @Override
    public List<Flight> getAllFlights() {
        return flightRepository.findAll();
    }

    @Override
    public List<Flight> getFlightsByAirplaneType(EFlightAirplane airplaneType) {
        return List.of ();
    }

    @Override
    public Flight updateFlight(Long id, Flight flightDetails) {
        Flight existingFlight = getFlightById(id);
        if (existingFlight != null) {
            existingFlight.setFlightNumber(flightDetails.getFlightNumber());
            existingFlight.setDepartureTime(flightDetails.getDepartureTime());
            existingFlight.setArrivalTime(flightDetails.getArrivalTime());
            existingFlight.setFlightAirplane(flightDetails.getFlightAirplane());
            existingFlight.setCapacityPlane(flightDetails.getCapacityPlane());
            existingFlight.setAvailability(flightDetails.isAvailability());
            existingFlight.setNumRows(flightDetails.getNumRows());
            existingFlight.setFlightPrice(flightDetails.getFlightPrice());

            existingFlight.setFlightDuration(flightDurationService.calculateFlightDuration(existingFlight));
            return flightRepository.save(existingFlight);
        }
        return null;
    }

    @Override
    public void updateFlightAvailability() {
        List<Flight> flights = flightRepository.findAll();
        LocalDateTime now = LocalDateTime.now();

        for (Flight flight : flights) {
            boolean allSeatsBooked = flight.getSeats().stream().allMatch(Seat::isBooked);

            if (allSeatsBooked && (flight.getDepartureTime().isBefore(now) || flight.getDepartureTime().isAfter(now))) {
                flight.setAvailability(false);
                flightRepository.save(flight);
            }
        }
    }

    /**
     * Deletes a flight by its ID.
     *
     * @param id the ID of the flight to delete
     * @return true if the flight was deleted, false if it did not exist
     */
    @Override
    public boolean deleteFlight(Long id) {
        if (flightRepository.existsById(id)) {
            flightRepository.deleteById(id);  // Cambia a deleteById()
            return true;
        }
        return false;
    }

    @Override
    public void cancelFlight(Long id) {
        Flight flight = getFlightById(id);
        if (flight != null) {
            flight.setAvailability(false);
            flightRepository.save(flight);
        }
    }

    @Override
    public void delayFlight(Long id, LocalDateTime departureTime) {
        Flight flight = getFlightById(id);
        if (flight != null) {
            flight.setDepartureTime(departureTime);
            flight.setFlightDuration(flightDurationService.calculateFlightDuration(flight));
            flightRepository.save(flight);
        }
    }


}



