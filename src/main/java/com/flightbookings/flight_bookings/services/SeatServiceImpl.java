package com.flightbookings.flight_bookings.services;

import com.flightbookings.flight_bookings.exceptions.SeatAlreadyBookedException;
import com.flightbookings.flight_bookings.exceptions.SeatNotFoundException;
import com.flightbookings.flight_bookings.models.ESeatLetter;
import com.flightbookings.flight_bookings.models.Flight;
import com.flightbookings.flight_bookings.models.Seat;
import com.flightbookings.flight_bookings.repositories.ISeatRepository;
import com.flightbookings.flight_bookings.services.interfaces.SeatService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
public class SeatServiceImpl implements SeatService {

    private final ISeatRepository seatRepository;

    public SeatServiceImpl(ISeatRepository seatRepository) {
        this.seatRepository = seatRepository;
    }

    @Override
    public List<String> initializeSeats(Flight flight, int numRows) {
        List<Seat> seats = flight.getSeats(); // Get the existing seats list from the flight
        List<String> seatIdentifiers = new ArrayList<>();

        for (int row = 1; row <= numRows; row++) {
            for (ESeatLetter letter : ESeatLetter.values()) {
                String seatName = row + letter.name();
                Seat seat = new Seat(null, row, letter, false, flight, null);
                seat.setSeatName(seatName);
                seats.add(seat); // Add the new seat to the existing list
                seatIdentifiers.add(seatName);
            }
        }

        seatRepository.saveAll(seats);

        return seatIdentifiers;
    }


    @Override
    @Transactional
    public Seat reserveSeat(Flight flight, String seatName) {
        // Find the seat by flight and seat name
        Seat seat = seatRepository.findByFlightAndSeatName(flight, seatName)
                .orElseThrow(() -> new SeatNotFoundException("Seat not found"));

        // Check if the seat is already booked
        if (seat.isBooked()) {
            throw new SeatAlreadyBookedException("Seat is already booked");
        }

        // Mark the seat as booked
        seat.setBooked(true);

        // Save the updated seat and return it
        return seatRepository.save(seat);
    }

}
