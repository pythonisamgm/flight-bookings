package com.flightbookings.flight_bookings.services;

import com.flightbookings.flight_bookings.models.PassengerEntity;
import com.flightbookings.flight_bookings.repositories.IPassengerRepository;
import com.flightbookings.flight_bookings.services.interfaces.PassengerService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
/**
 * Implementation of the PassengerService interface for managing passenger operations.
 */
@Service
@RequiredArgsConstructor
public class PassengerServiceImpl implements PassengerService {

    private final IPassengerRepository passengerRepository;

    @Override
    public PassengerEntity createPassenger(PassengerEntity passenger) {
        return passengerRepository.save(passenger);
    }

    @Override
    public PassengerEntity getPassengerById(Long id) {
        Optional<PassengerEntity> passenger = passengerRepository.findById(id);
        return passenger.orElse(null);
    }

    @Override
    public List<PassengerEntity> getAllPassengers() {
        return passengerRepository.findAll();
    }

    @Override
    public PassengerEntity updatePassenger(Long id, PassengerEntity passengerDetails) {
        Optional<PassengerEntity> existingPassenger = passengerRepository.findById(id);
        if (existingPassenger.isPresent()) {
            PassengerEntity passengerToUpdate = existingPassenger.get();
            passengerToUpdate.setPassengerName(passengerDetails.getPassengerName());
            passengerToUpdate.setIdentityDoc(passengerDetails.getIdentityDoc());
            passengerToUpdate.setTelephone(passengerDetails.getTelephone());
            passengerToUpdate.setNationality(passengerDetails.getNationality());
            return passengerRepository.save(passengerToUpdate);
        }
        return null;
    }

    @Override
    public boolean deletePassenger(Long id) {
        Optional<PassengerEntity> passenger = passengerRepository.findById(id);
        if (passenger.isPresent()) {
            passengerRepository.deleteById(id);
            return true;
        }
        return false;
    }
}

