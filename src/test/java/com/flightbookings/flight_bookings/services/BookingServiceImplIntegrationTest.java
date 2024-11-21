package com.flightbookings.flight_bookings.services;

import com.flightbookings.flight_bookings.models.*;
import com.flightbookings.flight_bookings.repositories.*;
import com.flightbookings.flight_bookings.services.interfaces.BookingService;
import com.flightbookings.flight_bookings.services.interfaces.FlightService;
import com.flightbookings.flight_bookings.services.interfaces.SeatService;
import org.instancio.Instancio;
import org.instancio.Select;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@ActiveProfiles("test")
@Transactional
class BookingServiceImplIntegrationTest {

    @Autowired
    private BookingService bookingService;

    @Autowired
    private IBookingRepository bookingRepository;

    @Autowired
    private IFlightRepository flightRepository;

    @Autowired
    private IPassengerRepository passengerRepository;

    @Autowired
    private IUserRepository userRepository;

    @Autowired
    private SeatService seatService;

    @Autowired
    private ISeatRepository seatRepository;

    private FlightEntity flight;
    private PassengerEntity passenger;
    private UserEntity user;
    private SeatEntity seat;
    @Autowired
    private FlightService flightService;

    @Test
    void createBooking_ShouldSaveAndReturnBooking() {
        FlightEntity flight = Instancio.of(FlightEntity.class)
                .set(Select.field(FlightEntity::getFlightId), 1L)
                .create();
        flightRepository.save(flight);

        PassengerEntity passenger = Instancio.of(PassengerEntity.class)
                .set(Select.field(PassengerEntity::getPassengerId), 1L)
                .create();
        passengerRepository.save(passenger);

        UserEntity user = Instancio.of(UserEntity.class)
                .set(Select.field(UserEntity::getUserId), 1L)
                .create();
        userRepository.save(user);

        SeatEntity seat = new SeatEntity();
        seat.setSeatName("1A");
        seat.setBooked(false);
        seat.setFlight(flight);
        seatRepository.save(seat);

        BookingEntity booking = bookingService.createBooking(
                flight.getFlightId(),
                passenger.getPassengerId(),
                "1A",
                user.getUserId()
        );

        assertThat(booking).isNotNull();
        assertThat(booking.getFlight()).isEqualTo(flight);
        assertThat(booking.getPassenger()).isEqualTo(passenger);
        assertThat(booking.getSeat()).isEqualTo(seat);
        assertThat(booking.getUser()).isEqualTo(user);
        assertThat(booking.getDateOfBooking()).isBeforeOrEqualTo(LocalDateTime.now());

        BookingEntity savedBooking = bookingRepository.findById(booking.getBookingId()).orElse(null);
        assertThat(savedBooking).isNotNull();
        assertThat(savedBooking.getFlight()).isEqualTo(flight);
        assertThat(savedBooking.getPassenger()).isEqualTo(passenger);
        assertThat(savedBooking.getSeat()).isEqualTo(seat);
        assertThat(savedBooking.getUser()).isEqualTo(user);
    }
}


