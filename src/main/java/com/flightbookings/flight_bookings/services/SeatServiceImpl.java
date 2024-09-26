package com.flightbookings.flight_bookings.services;

import com.flightbookings.flight_bookings.exceptions.SeatAlreadyBookedException;
import com.flightbookings.flight_bookings.exceptions.SeatNotFoundException;
import com.flightbookings.flight_bookings.models.ESeatLetter;
import com.flightbookings.flight_bookings.models.Flight;
import com.flightbookings.flight_bookings.models.Seat;
import com.flightbookings.flight_bookings.repositories.ISeatRepository;
import com.flightbookings.flight_bookings.services.interfaces.SeatService;
import org.springframework.stereotype.Service;

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
        List<Seat> seats = new ArrayList<>();
        List<String> seatIdentifiers = new ArrayList<>();

        for (int row = 1; row <= numRows; row++) {
            for (ESeatLetter letter : ESeatLetter.values()) {
                String seatName = row + letter.name();
                Seat seat = new Seat(null, row, letter, false, flight, null);
                seat.setSeatName(seatName);
                seats.add(seat);
                seatIdentifiers.add(seatName);
            }
        }

        seatRepository.saveAll(seats);
        flight.setSeats(seats);

        return seatIdentifiers;
    }

    @Override
    public Seat reserveSeat(Flight flight, String seatName) {
        Seat seat = seatRepository.findBySeatReference(flight, seatName)
                .orElseThrow(() -> new SeatNotFoundException("Seat not found"));

        if (seat.isBooked()) {
            throw new SeatAlreadyBookedException("Seat is already booked");
        }

        seat.setBooked(true);
        seatRepository.save(seat);

        return seat;
    }
}
