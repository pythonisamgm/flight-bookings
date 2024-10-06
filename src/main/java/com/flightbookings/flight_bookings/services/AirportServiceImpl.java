//package com.flightbookings.flight_bookings.services;
//
//import com.flightbookings.flight_bookings.models.Airport;
//import com.flightbookings.flight_bookings.repositories.IAirportRepository;
//import com.flightbookings.flight_bookings.services.interfaces.AirportService;
//import org.springframework.stereotype.Service;
//
//import java.util.List;
//import java.util.Optional;
//import java.util.Set;
//
//@Service
//public class AirportServiceImpl implements AirportService {
//
//    private final IAirportRepository airportRepository;
//
//    public AirportServiceImpl(IAirportRepository airportRepository) {
//        this.airportRepository = airportRepository;
//    }
//
//    public Airport createAirport(Airport airport){
//        return airportRepository.save(airport);
//    }
//    public List<Airport> createAirports(Set<Airport> airports) {
//        return airportRepository.saveAll(airports);
//    }
//    public List<Airport> getAllAirports(){
//        return airportRepository.findAll();
//    }
//
//    @Override
//    public Optional<Airport> getAirportById(String id) {
//        return airportRepository.findById(id);
//    }
// }