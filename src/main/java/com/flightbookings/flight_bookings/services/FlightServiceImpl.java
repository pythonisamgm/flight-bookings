package com.flightbookings.flight_bookings.services;

import com.flightbookings.flight_bookings.models.Flight;
import com.flightbookings.flight_bookings.models.EFlightAirplane;
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

@Service
public class FlightServiceImpl implements FlightService {

    private final IFlightRepository flightRepository;
    private final FlightDurationService flightDurationService;

    public FlightServiceImpl(IFlightRepository flightRepository, FlightDurationService flightDurationService) {
        this.flightRepository = flightRepository;
        this.flightDurationService = flightDurationService;
    }

    @Override
    public Flight createFlight(Flight flight) {
        // Calcula la duración antes de guardar el vuelo
        flight.setFlightDuration(flightDurationService.calculateFlightDuration(flight));
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

            // Recalcula la duración después de la actualización
            existingFlight.setFlightDuration(flightDurationService.calculateFlightDuration(existingFlight));
            return flightRepository.save(existingFlight);
        }
        return null;
    }

    @Override
    public boolean deleteFlight(Long id) {
        Flight flight = getFlightById(id);
        if (flight != null) {
            flightRepository.delete(flight);
            return true;
        }
        return false;
    }

    @Override
    public void cancelFlight(Long id) {
        // Implementación de la lógica para cancelar el vuelo
    }

    @Override
    public void delayFlight(Long id, LocalDateTime departureTime) {
        Flight flight = getFlightById(id);
        if (flight != null) {
            flight.setDepartureTime(departureTime);
            // Recalcula la duración después de cambiar la hora de salida
            flight.setFlightDuration(flightDurationService.calculateFlightDuration(flight));
            flightRepository.save(flight);
        }
    }

    @Override
    public void updateFlightAvailability() {
        // Implementación de la lógica para actualizar la disponibilidad del vuelo
    }

    @Override
    public List<Flight> getFlightsByAirplaneType(EFlightAirplane airplaneType) {
        // Implementación de la lógica para obtener vuelos por tipo de avión
        return null;
    }

//    public List<Flight> searchFlightsByCity(String city) {
//        return flightRepository.findAll()
//                .stream()
//                .filter(flight -> flight.getAirports().stream().anyMatch(airport -> airport.getCity().equalsIgnoreCase(city)))
//                .collect(Collectors.toList());
//    }


}
