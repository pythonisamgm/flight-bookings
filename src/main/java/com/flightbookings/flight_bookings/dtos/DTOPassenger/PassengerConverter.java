package com.flightbookings.flight_bookings.dtos.DTOPassenger;

import com.flightbookings.flight_bookings.exceptions.BookingNotFoundException;
import com.flightbookings.flight_bookings.models.Booking;
import com.flightbookings.flight_bookings.models.Passenger;
import com.flightbookings.flight_bookings.services.interfaces.BookingService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Converter class for converting between Passenger and PassengerDTO objects.
 * This class utilizes ModelMapper for object mapping.
 */
@Component
public class PassengerConverter {

    private final BookingService bookingService;

    /**
     * Constructs a PassengerConverter with the specified ModelMapper.
     *
     * @param bookingService the BookingService used for retrieving booking details
     */
    public PassengerConverter(BookingService bookingService) {
        this.bookingService = bookingService;
    }
    /**
     * Converts a PassengerDTO to a Passenger entity.
     *
     * @param passengerDTO the PassengerDTO to convert
     * @return the converted Passenger entity
     */
    public Passenger dtoToPassenger(PassengerDTO passengerDTO) {
        Passenger passenger = new Passenger();

        passenger.setPassengerId(passengerDTO.getPassengerId());
        passenger.setPassengerName(passengerDTO.getPassengerName());
        passenger.setIdentityDoc(passengerDTO.getIdentityDoc());
        passenger.setTelephone(passengerDTO.getTelephone());
        passenger.setNationality(passengerDTO.getNationality());

        if (passengerDTO.getBookingId() != null) {
            Booking booking = bookingService.getGeneralBookingById(passengerDTO.getBookingId())
                    .orElseThrow(() -> new BookingNotFoundException("Booking not found for ID: " + passengerDTO.getBookingId()));
            passenger.setBooking(booking);
        }

        return passenger;
    }


            /**
             * Converts a Passenger entity to a PassengerDTO.
             *
             * @param passenger the Passenger entity to convert
             * @return the converted PassengerDTO
             */
            public PassengerDTO passengerToDto(Passenger passenger) {
                PassengerDTO passengerDTO = new PassengerDTO();

                passengerDTO.setPassengerId(passenger.getPassengerId());
                passengerDTO.setPassengerName(passenger.getPassengerName());
                passengerDTO.setIdentityDoc(passenger.getIdentityDoc());
                passengerDTO.setTelephone(passenger.getTelephone());
                passengerDTO.setNationality(passenger.getNationality());

                if (passenger.getBooking() != null) {
                    passengerDTO.setBookingId(passenger.getBooking().getBookingId());
                } else {
                    throw new BookingNotFoundException("Booking not found for passenger with ID: " + passenger.getPassengerId());
                }

                return passengerDTO;
            }

            /**
             * Converts a list of PassengerDTOs to a list of Passenger entities.
             *
             * @param passengerDTOs the list of PassengerDTOs to convert
             * @return the list of converted Passenger entities
             */
            public List<Passenger> dtoListToPassengers (List < PassengerDTO > passengerDTOs) {
                return passengerDTOs.stream()
                        .map(this::dtoToPassenger)
                        .collect(Collectors.toList());
            }
    /**
     * Converts a list of Passenger entities to a list of PassengerDTOs.
     *
     * @param passengers the list of Passenger entities to convert
     * @return a list of converted PassengerDTOs
     */
    public List<PassengerDTO> passengersToDtoList(List<Passenger> passengers) {
        return passengers.stream()
                .map(this::passengerToDto)
                .collect(Collectors.toList());
    }
        }