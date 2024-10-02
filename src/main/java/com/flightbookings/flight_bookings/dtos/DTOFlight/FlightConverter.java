package com.flightbookings.flight_bookings.dtos.DTOFlight;

import com.flightbookings.flight_bookings.models.EFlightAirplane;
import com.flightbookings.flight_bookings.models.Flight;
import com.flightbookings.flight_bookings.models.Seat;
import com.flightbookings.flight_bookings.models.Booking;
import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;

public class FlightConverter {

    private final ModelMapper modelMapper;

    public FlightConverter(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
        configure();
    }

    private void configure() {
        modelMapper.addMappings(new PropertyMap<Flight, FlightDTO>() {
            @Override
            protected void configure() {
                map().setFlightAirplane(source.getFlightAirplane().name());
                map().setSeatIds(source.getSeats() != null
                        ? source.getSeats().stream().map(Seat::getSeatId).toList()
                        : null);
                map().setBookingIds(source.getBookingList() != null
                        ? source.getBookingList().stream().map(Booking::getBookingId).toList()
                        : null);
            }
        });

        modelMapper.addMappings(new PropertyMap<FlightDTO, Flight>() {
            @Override
            protected void configure() {
                using(airplaneConverter()).map(source.getFlightAirplane()).setFlightAirplane(null);
            }
        });
    }

    private EFlightAirplane airplaneConverter() {
        return context -> context.getSource() != null ? EFlightAirplane.valueOf(context.getSource()) : null;
    }

    public FlightDTO convertToDto(Flight flight) {
        return modelMapper.map(flight, FlightDTO.class);
    }

    public Flight convertToEntity(FlightDTO flightDTO) {
        return modelMapper.map(flightDTO, Flight.class);
    }
}
