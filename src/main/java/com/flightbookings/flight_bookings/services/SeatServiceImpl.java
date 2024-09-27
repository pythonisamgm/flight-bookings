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
    @Transactional
    public List<String> initializeSeats(Flight flight, int numRows) {
        List<String> seatIdentifiers = new ArrayList<>();
        List<Seat> seats = new ArrayList<>();

        for (int row = 1; row <= numRows; row++) {
            for (ESeatLetter letter : ESeatLetter.values()) {
                String seatName = row + letter.name();

                boolean seatExists = seatRepository.findByFlightAndSeatName(flight, seatName).isPresent();
                if (!seatExists) {
                    Seat seat = new Seat(null, row, letter, false, flight, null);
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
    public Seat reserveSeat(Flight flight, String seatName) {
        Seat seat = seatRepository.findByFlightAndSeatName(flight, seatName)
                .orElseThrow(() -> new SeatNotFoundException("Seat not found"));

        if (seat.isBooked()) {
            throw new SeatAlreadyBookedException("Seat is already booked");
        }

        seat.setBooked(true);

        return seatRepository.save(seat);
    }

}
