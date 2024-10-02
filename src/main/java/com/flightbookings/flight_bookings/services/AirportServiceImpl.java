package com.flightbookings.flight_bookings.services;

import com.flightbookings.flight_bookings.models.Airport;
import com.flightbookings.flight_bookings.repositories.IAirportRepository;
import com.flightbookings.flight_bookings.services.interfaces.AirportService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AirportServiceImpl implements AirportService {

    private final IAirportRepository airportRepository;

    public AirportServiceImpl(IAirportRepository airportRepository) {
        this.airportRepository = airportRepository;
    }
    @Override
    public Airport createAirport(Airport airport){
        return airportRepository.save(airport);
    }
    @Override
    public Airport updateAirport(Airport airport, Long id){
        airport.setAirportId(id);
        return airportRepository.save(airport);
    }
    @Override
    public List<Airport> getAllAirports(){
        return airportRepository.findAll();
    }
    @Override
   public Optional<Airport> getAirportById(Long id) {
        return airportRepository.findById(id);
    }
}
