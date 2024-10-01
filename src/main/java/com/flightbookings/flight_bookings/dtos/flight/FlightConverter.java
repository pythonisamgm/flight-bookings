package com.flightbookings.flight_bookings.dtos.flight;

import com.flightbookings.flight_bookings.models.EFlightAirplane;
import com.flightbookings.flight_bookings.models.Flight;

public class FlightConverter {

    public FlightDTO flightToDto(Flight flight) {
        FlightDTO flightDTO = new FlightDTO();
        flightDTO.setFlightId(flight.getFlightId());
        flightDTO.setFlightNumber(flight.getFlightNumber());
        flightDTO.setDepartureTime(flight.getDepartureTime());
        flightDTO.setArrivalTime(flight.getArrivalTime());
        flightDTO.setFlightAirplane(flight.getFlightAirplane().name());
        flightDTO.setCapacityPlane(flight.getCapacityPlane());
        flightDTO.setAvailability(flight.isAvailability());
        flightDTO.setFlightPrice(flight.getFlightPrice());
        return flightDTO;
    }

    public Flight dtoToFlight(FlightDTO flightDTO) {
        Flight flight = new Flight();
        flight.setFlightId(flightDTO.getFlightId());
        flight.setFlightNumber(flightDTO.getFlightNumber());
        flight.setDepartureTime(flightDTO.getDepartureTime());
        flight.setArrivalTime(flightDTO.getArrivalTime());
        flight.setCapacityPlane(flightDTO.getCapacityPlane());
        flight.setAvailability(flightDTO.isAvailability());
        flight.setFlightAirplane(EFlightAirplane.valueOf(flightDTO.getFlightAirplane()));
        flight.setFlightPrice(flightDTO.getFlightPrice());
        return flight;
    }
}
