package com.flightbookings.flight_bookings.dtos.passenger;
import com.flightbookings.flight_bookings.models.Passenger;

public class PassengerConverter {

    public PassengerDTO passengerToDto(Passenger passenger) {
        PassengerDTO passengerDTO = new PassengerDTO();
        passengerDTO.setPassengerId(passenger.getPassengerId());
        passengerDTO.setPassengerName(passenger.getPassengerName());
        passengerDTO.setIdentityDoc(passenger.getIdentityDoc());
        passengerDTO.setTelephone(passenger.getTelephone());
        passengerDTO.setNationality(passenger.getNationality());
        passengerDTO.setBookingId(passenger.getBooking() != null ? passenger.getBooking().getBookingId() : null);
        return passengerDTO;
    }

    public Passenger dtoToPassenger(PassengerDTO passengerDTO) {
        Passenger passenger = new Passenger();
        passenger.setPassengerId(passengerDTO.getPassengerId());
        passenger.setPassengerName(passengerDTO.getPassengerName());
        passenger.setIdentityDoc(passengerDTO.getIdentityDoc());
        passenger.setTelephone(passengerDTO.getTelephone());
        passenger.setNationality(passengerDTO.getNationality());
        return passenger;
    }
}
