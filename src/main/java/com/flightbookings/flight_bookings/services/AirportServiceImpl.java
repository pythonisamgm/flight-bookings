package com.flightbookings.flight_bookings.services;

import com.flightbookings.flight_bookings.models.Airport;
import com.flightbookings.flight_bookings.repositories.IAirportRepository;
import com.flightbookings.flight_bookings.services.interfaces.AirportService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Set;
/**
 * Service implementation for managing airport operations in the flight booking system.
 * This class provides methods to create, retrieve, and manage airports.
 */
@Service
public class AirportServiceImpl implements AirportService {

    private final IAirportRepository airportRepository;
    /**
     * Constructs an AirportServiceImpl with the specified Airport repository.
     *
     * @param airportRepository the repository for airport data.
     */
    public AirportServiceImpl(IAirportRepository airportRepository) {
        this.airportRepository = airportRepository;
    }
    /**
     * Creates a new airport in the system.
     *
     * @param airport the Airport object to be created.
     * @return the created Airport object.
     */
    public Airport createAirport(Airport airport){
        return airportRepository.save(airport);
    }
    /**
     * Creates multiple airports in the system.
     *
     * @param airports a set of Airport objects to be created.
     * @return a list of the created Airport objects.
     */
    public List<Airport> createAirports(Set<Airport> airports) {
        return airportRepository.saveAll(airports);
    }
    /**
     * Retrieves all airports in the system.
     *
     * @return a list of all Airport objects.
     */
    public List<Airport> getAllAirports(){
        return airportRepository.findAll();
    }
    /**
     * Retrieves an airport by its unique identifier.
     *
     * @param id the unique identifier of the airport.
     * @return an Optional containing the Airport object if found, or empty if not found.
     */
    @Override
    public Optional<Airport> getAirportById(String id) {
        return airportRepository.findById(id);
    }
 }