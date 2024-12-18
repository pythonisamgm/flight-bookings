package com.flightbookings.flight_bookings.services;

import com.flightbookings.flight_bookings.exceptions.FlightNotFoundException;
import com.flightbookings.flight_bookings.models.Flight;
import com.flightbookings.flight_bookings.models.EFlightAirplane;
import com.flightbookings.flight_bookings.models.Seat;
import com.flightbookings.flight_bookings.repositories.IFlightRepository;
import com.flightbookings.flight_bookings.repositories.ISeatRepository;
import com.flightbookings.flight_bookings.services.interfaces.FlightService;
import com.flightbookings.flight_bookings.services.interfaces.SeatService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
/**
 * Service implementation for managing flight-related operations.
 */
@Service
public class FlightServiceImpl implements FlightService {

    private final IFlightRepository flightRepository;
    private final ISeatRepository seatRepository;
    private final SeatService seatService;
    //private final IAirportRepository airportRepository;
    /**
     * Constructs a new FlightServiceImpl with the specified dependencies.
     *
     * @param flightRepository the flight repository
     * @param seatRepository the seat repository
     * @param seatService the seat service for managing seat operations
     */
    public FlightServiceImpl(IFlightRepository flightRepository, ISeatRepository seatRepository, SeatService seatService) {
        this.flightRepository = flightRepository;
        this.seatRepository = seatRepository;
        //this.airportRepository = airportRepository;
        this.seatService = seatService;
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

    /**
     * Retrieves a list of flights that use a specified airplane type.
     *
     * @param airplaneType the type of airplane to filter flights by
     * @return a list of Flight objects that match the specified airplane type
     */
    public List<Flight> getFlightsByAirplaneType(EFlightAirplane airplaneType) {
        return flightRepository.findAll()
                .stream()
                .filter(flight -> flight.getFlightAirplane() == airplaneType)
                .collect(Collectors.toList());
    }


    /**
     * Updates the availability of all flights based on their arrival time and seat availability.
     * If a flight's arrival time has passed or there are no seats available, its availability is set to false.
     */
    @Override
    public void updateFlightAvailability() {
        LocalDateTime now = LocalDateTime.now();
        List<Flight> flights = flightRepository.findAll();

        for (Flight flight : flights) {
            boolean allSeatsBooked = flight.getSeats().stream().allMatch(Seat::isBooked);

            if (flight.getDepartureTime().isBefore(now) || allSeatsBooked) {
                flight.setAvailability(false);
                flightRepository.save(flight);
            }
        }
    }

    /**
     * Updates the details of an existing flight.
     *
     * @param updatedFlight the Flight object with updated details
     * @return the updated Flight object, or null if the flight does not exist
     */
    @Override
    public Flight updateFlight(Flight updatedFlight) {
        Optional<Flight> existingFlight = flightRepository.findById(updatedFlight.getFlightId());
        if (existingFlight.isPresent()) {
            Flight flight = existingFlight.get();
            flight.setFlightNumber(updatedFlight.getFlightNumber());
            flight.setDepartureTime(updatedFlight.getDepartureTime());
            flight.setArrivalTime(updatedFlight.getArrivalTime());
            flight.setFlightAirplane(updatedFlight.getFlightAirplane());
            flight.setCapacityPlane(updatedFlight.getCapacityPlane());
            flight.setAvailability(updatedFlight.isAvailability());
            flight.setFlightPrice(updatedFlight.getFlightPrice());
            flight.setOriginAirport(updatedFlight.getOriginAirport());
            flight.setDestinationAirport(updatedFlight.getDestinationAirport());
            return flightRepository.save(flight);
        } else {
            throw new FlightNotFoundException("Flight with ID " + updatedFlight.getFlightId() + " not found");
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
            flightRepository.deleteById(id);
            return true;
        }
        return false;
    }

}