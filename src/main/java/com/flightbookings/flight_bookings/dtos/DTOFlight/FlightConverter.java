package com.flightbookings.flight_bookings.dtos.DTOFlight;


import com.flightbookings.flight_bookings.models.EFlightAirplane;
import com.flightbookings.flight_bookings.models.Flight;
import com.flightbookings.flight_bookings.models.Seat;
import com.flightbookings.flight_bookings.models.Booking;

import java.util.stream.Collectors;

public class FlightConverter {

    public static FlightDTO toDTO(Flight flight) {
        if (flight == null) {
            return null;
        }
        FlightDTO dto = new FlightDTO();
        dto.setFlightId(flight.getFlightId());
        dto.setFlightNumber(flight.getFlightNumber());
        dto.setDepartureTime(flight.getDepartureTime());
        dto.setArrivalTime(flight.getArrivalTime());
        dto.setFlightAirplane(flight.getFlightAirplane().name());
        dto.setCapacityPlane(flight.getCapacityPlane());
        dto.setAvailability(flight.isAvailability());
        dto.setNumRows(flight.getNumRows());
        dto.setFlightPrice(flight.getFlightPrice());
        dto.setSeatIds(flight.getSeats().stream().map(Seat::getSeatId).collect(Collectors.toList()));
        dto.setBookingIds(flight.getBookingList().stream().map(Booking::getBookingId).collect(Collectors.toList()));

        return dto;
    }

    public static Flight toEntity(FlightDTO dto) {
        if (dto == null) {
            return null;
        }
        Flight flight = new Flight();
        flight.setFlightId(dto.getFlightId());
        flight.setFlightNumber(dto.getFlightNumber());
        flight.setDepartureTime(dto.getDepartureTime());
        flight.setArrivalTime(dto.getArrivalTime());
        flight.setFlightAirplane(EFlightAirplane.valueOf(dto.getFlightAirplane()));
        flight.setCapacityPlane(dto.getCapacityPlane());
        flight.setAvailability(dto.isAvailability());
        flight.setNumRows(dto.getNumRows());
        flight.setFlightPrice(dto.getFlightPrice());
        return flight;
    }
}
