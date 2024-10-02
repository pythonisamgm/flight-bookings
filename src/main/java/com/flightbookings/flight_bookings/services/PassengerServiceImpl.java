package com.flightbookings.flight_bookings.services;

import com.flightbookings.flight_bookings.dtos.DTOPassenger.PassengerConverter;
import com.flightbookings.flight_bookings.dtos.DTOPassenger.PassengerDTO;
import com.flightbookings.flight_bookings.models.Passenger;
import com.flightbookings.flight_bookings.repositories.IPassengerRepository;
import com.flightbookings.flight_bookings.services.interfaces.PassengerService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class PassengerServiceImpl implements PassengerService {

    private final IPassengerRepository passengerRepository;
    private final PassengerConverter passengerConverter;

    public PassengerServiceImpl(IPassengerRepository passengerRepository, PassengerConverter passengerConverter) {
        this.passengerRepository = passengerRepository;
        this.passengerConverter = passengerConverter;
    }

    @Override
    public PassengerDTO createPassenger(PassengerDTO passengerDTO) {
        Passenger passenger = passengerConverter.toEntity(passengerDTO);
        Passenger savedPassenger = passengerRepository.save(passenger);
        return passengerConverter.toDTO(savedPassenger);
    }

    @Override
    public PassengerDTO getPassengerById(Long id) {
        Optional<Passenger> passenger = passengerRepository.findById(id);
        return passenger.map(passengerConverter::toDTO).orElse(null);
    }

    @Override
    public List<PassengerDTO> getAllPassengers() {
        return passengerRepository.findAll().stream()
                .map(passengerConverter::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public PassengerDTO updatePassenger(Long id, PassengerDTO passengerDetails) {
        Optional<Passenger> existingPassenger = passengerRepository.findById(id);
        if (existingPassenger.isPresent()) {
            Passenger passengerToUpdate = existingPassenger.get();
            passengerToUpdate.setPassengerName(passengerDetails.getPassengerName());
            passengerToUpdate.setIdentityDoc(passengerDetails.getIdentityDoc());
            passengerToUpdate.setTelephone(passengerDetails.getTelephone());
            passengerToUpdate.setNationality(passengerDetails.getNationality());
            Passenger updatedPassenger = passengerRepository.save(passengerToUpdate);
            return passengerConverter.toDTO(updatedPassenger);
        }
        return null;
    }

    @Override
    public boolean deletePassenger(Long id) {
        Optional<Passenger> passenger = passengerRepository.findById(id);
        if (passenger.isPresent()) {
            passengerRepository.deleteById(id);
            return true;
        }
        return false;
    }
}
