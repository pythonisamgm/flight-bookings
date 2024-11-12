package com.flightbookings.flight_bookings.services;

import com.flightbookings.flight_bookings.exceptions.FlightNotFoundException;
import com.flightbookings.flight_bookings.models.FlightEntity;
import com.flightbookings.flight_bookings.models.EFlightAirplane;
import com.flightbookings.flight_bookings.models.SeatEntity;
import com.flightbookings.flight_bookings.repositories.IFlightRepository;
import com.flightbookings.flight_bookings.repositories.ISeatRepository;
import com.flightbookings.flight_bookings.services.interfaces.FlightService;
import com.flightbookings.flight_bookings.services.interfaces.SeatService;
import lombok.RequiredArgsConstructor;
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
@RequiredArgsConstructor
public class FlightServiceImpl implements FlightService {

    private final IFlightRepository flightRepository;
    private final ISeatRepository seatRepository;
    private final SeatService seatService;

    /**
     * Creates a new flight and initializes its seats.
     *
     * @param flight the Flight object to be created
     * @return the saved Flight object with initialized seats
     */
    @Override
    @Transactional
    public FlightEntity createFlight(FlightEntity flight) {
        flight.setSeats(new ArrayList<>());
        FlightEntity savedFlight = flightRepository.save(flight);
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
    public FlightEntity getFlightById(Long id) {
        return flightRepository.findById(id).orElse(null);
    }
    /**
     * Retrieves all flights from the database.
     *
     * @return a list of all Flight objects
     */
    @Override
    public List<FlightEntity> getAllFlights() {
        return flightRepository.findAll();
    }

    /**
     * Retrieves a list of flights that use a specified airplane type.
     *
     * @param airplaneType the type of airplane to filter flights by
     * @return a list of Flight objects that match the specified airplane type
     */
    public List<FlightEntity> getFlightsByAirplaneType(EFlightAirplane airplaneType) {
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
        List<FlightEntity> flights = flightRepository.findAll();

        for (FlightEntity flight : flights) {
            boolean allSeatsBooked = flight.getSeats().stream().allMatch(SeatEntity::isBooked);

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
    public FlightEntity updateFlight(FlightEntity updatedFlight) {
        Optional<FlightEntity> existingFlight = flightRepository.findById(updatedFlight.getFlightId());
        if (existingFlight.isPresent()) {
            FlightEntity flight = existingFlight.get();
            flight.setFlightNumber(updatedFlight.getFlightNumber());
            flight.setDepartureTime(updatedFlight.getDepartureTime());
            flight.setArrivalTime(updatedFlight.getArrivalTime());
            flight.setFlightDuration(updatedFlight.getFlightDuration());
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