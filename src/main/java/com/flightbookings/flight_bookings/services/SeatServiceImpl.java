package com.flightbookings.flight_bookings.services;

import com.flightbookings.flight_bookings.exceptions.SeatAlreadyBookedException;
import com.flightbookings.flight_bookings.exceptions.SeatNotFoundException;
import com.flightbookings.flight_bookings.models.ESeatLetter;
import com.flightbookings.flight_bookings.models.FlightEntity;
import com.flightbookings.flight_bookings.models.SeatEntity;
import com.flightbookings.flight_bookings.repositories.ISeatRepository;
import com.flightbookings.flight_bookings.services.interfaces.FlightService;
import com.flightbookings.flight_bookings.services.interfaces.SeatService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
/**
 * Implementation of the SeatService interface for managing seat operations.
 */
@Service
@RequiredArgsConstructor
public class SeatServiceImpl implements SeatService {

    private final ISeatRepository seatRepository;
    private final FlightService flightService;


    @Override
    public Optional<SeatEntity> getSeatById(Long seatId) {
        return seatRepository.findById(seatId);
    }

    @Override
    @Transactional
    public List<String> initializeSeats(FlightEntity flight, int numRows) {
        List<String> seatIdentifiers = new ArrayList<>();
        List<SeatEntity> seats = new ArrayList<>();

        for (int row = 1; row <= numRows; row++) {
            for (ESeatLetter letter : ESeatLetter.values()) {
                String seatName = row + letter.name();

                boolean seatExists = seatRepository.findByFlightAndSeatName(flight, seatName).isPresent();
                if (!seatExists) {
                    SeatEntity seat = new SeatEntity(null, row, letter, false, flight, null);
                    seat.setSeatName(seatName);
                    seats.add(seat);
                    seatIdentifiers.add(seatName);
                } else {
                    System.out.println("Seat " + seatName + " already exists. Skipping...");
                }
            }
        }
        if (!seats.isEmpty()) {
            seatRepository.saveAll(seats);
        }

        return seatIdentifiers;
    }

    @Override
    @Transactional
    public void initializeSeatsForAllFlights() {
        List<FlightEntity> flights = flightService.getAllFlights();

        for (FlightEntity flight : flights) {
            initializeSeats(flight, flight.getNumRows());
        }

        System.out.println("Seats initialized for all flights.");
    }

    @Override
    @Transactional
    public SeatEntity reserveSeat(FlightEntity flight, String seatName) {
        SeatEntity seat = seatRepository.findByFlightAndSeatName(flight, seatName)
                .orElseThrow(() -> new SeatNotFoundException("Seat not found"));

        if (seat.isBooked()) {
            throw new SeatAlreadyBookedException("Seat is already booked");
        }

        seat.setBooked(true);

        return seatRepository.save(seat);
    }

}
