package com.flightbookings.flight_bookings.services;

import com.flightbookings.flight_bookings.models.Passenger;
import com.flightbookings.flight_bookings.repositories.IPassengerRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PassengerService {

    private final IPassengerRepository passengerRepository;

    public PassengerService(IPassengerRepository passengerRepository) {
        this.passengerRepository = passengerRepository;
    }

    public Passenger createPassenger(Passenger passenger) {
        return passengerRepository.save(passenger);
    }

    public Passenger getPassengerById(Long id) {
        Optional<Passenger> passenger = passengerRepository.findById(id);
        return passenger.orElse(null);
    }

    public List<Passenger> getAllPassengers() {
        return passengerRepository.findAll();
    }

    public Passenger updatePassenger(Long id, Passenger passengerDetails) {
        Optional<Passenger> existingPassenger = passengerRepository.findById(id);
        if (existingPassenger.isPresent()) {
            Passenger passengerToUpdate = existingPassenger.get();
            passengerToUpdate.setPassengerName(passengerDetails.getPassengerName());
            passengerToUpdate.setIdentityDoc(passengerDetails.getIdentityDoc());
            passengerToUpdate.setTelephone(passengerDetails.getTelephone());
            passengerToUpdate.setNationality(passengerDetails.getNationality());
            return passengerRepository.save(passengerToUpdate);
        }
        return null;
    }

    public boolean deletePassenger(Long id) {
        Optional<Passenger> passenger = passengerRepository.findById(id);
        if (passenger.isPresent()) {
            passengerRepository.deleteById(id);
            return true;
        }
        return false;
    }
}
