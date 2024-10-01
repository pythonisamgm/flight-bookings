package com.flightbookings.flight_bookings.dtos.DTOPassenger;


import com.flightbookings.flight_bookings.models.Passenger;

public class PassengerConverter {

    public static PassengerDTO toDTO(Passenger passenger) {
        if (passenger == null) {
            return null;
        }
        PassengerDTO dto = new PassengerDTO();
        dto.setPassengerId(passenger.getPassengerId());
        dto.setPassengerName(passenger.getPassengerName());
        dto.setIdentityDoc(passenger.getIdentityDoc());
        dto.setTelephone(passenger.getTelephone());
        dto.setNationality(passenger.getNationality());
        dto.setBookingId(passenger.getBooking() != null ? passenger.getBooking().getBookingId() : null);
        return dto;
    }

    public static Passenger toEntity(PassengerDTO dto) {
        if (dto == null) {
            return null;
        }
        Passenger passenger = new Passenger();
        passenger.setPassengerId(dto.getPassengerId());
        passenger.setPassengerName(dto.getPassengerName());
        passenger.setIdentityDoc(dto.getIdentityDoc());
        passenger.setTelephone(dto.getTelephone());
        passenger.setNationality(dto.getNationality());
        return passenger;
    }
}

