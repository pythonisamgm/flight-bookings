package com.flightbookings.flight_bookings.services;

import com.flightbookings.flight_bookings.models.*;
import com.flightbookings.flight_bookings.repositories.*;
import com.flightbookings.flight_bookings.services.interfaces.BookingService;
import com.flightbookings.flight_bookings.services.interfaces.SeatService;
import org.instancio.Instancio;
import org.instancio.Select;
import org.instancio.settings.Settings;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.instancio.settings.Keys.FAIL_ON_ERROR;
import static org.instancio.settings.Keys.MAX_DEPTH;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

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

    private FlightEntity flight;
    private PassengerEntity passenger;
    private UserEntity user;
    private SeatEntity seat;

    @BeforeEach
    void setUp() {

        flight = flightRepository.save(Instancio.of(FlightEntity.class)
                .set(Select.field(FlightEntity::getFlightNumber), 123)
                .withSettings(Settings.create().set(FAIL_ON_ERROR, true))
                .create());

        passenger = passengerRepository.save(Instancio.of(PassengerEntity.class)
                .set(Select.field(PassengerEntity::getPassengerName), "Test")
                .set(Select.field(PassengerEntity::getNationality), "Spanish")
                .withSettings(Settings.create().set(FAIL_ON_ERROR, true))
                .create());

        user = userRepository.save(Instancio.of(UserEntity.class)
                .set(Select.field(UserEntity::getUsername), "testuser")
                .withSettings(Settings.create().set(FAIL_ON_ERROR, true))
                .create());

    }

    @Test
    void createBooking_ShouldSaveAndReturnBooking() {
        seat = Instancio.of(SeatEntity.class)
                .set(Select.field(SeatEntity::getSeatName), "1A")
                .set(Select.field(SeatEntity::getFlight), flight)
                .create();
        BookingEntity booking = Instancio.of(BookingEntity.class)
                .set(Select.field(BookingEntity::getFlight), flight)
                .set(Select.field(BookingEntity::getPassenger), passenger)
                .set(Select.field(BookingEntity::getUser), user)
                .set(Select.field(SeatEntity::getSeatName), "1A")
                .withSettings(Settings.create().set(FAIL_ON_ERROR, true).set(MAX_DEPTH, 5))
                .create();

        BookingEntity savedBooking = bookingService.createBooking(
                flight.getFlightId(),
                passenger.getPassengerId(),
                booking.getSeat().getSeatName(),
                user.getUserId()
        );

        assertThat(savedBooking).isNotNull();
        assertThat(savedBooking.getFlight()).isEqualTo(flight);
        assertThat(savedBooking.getPassenger()).isEqualTo(passenger);
        assertThat(savedBooking.getUser()).isEqualTo(user);
        assertThat(savedBooking.getSeat().getSeatName()).isEqualTo("1A");

        BookingEntity foundBooking = bookingRepository.findById(savedBooking.getBookingId()).orElse(null);
        assertThat(foundBooking).isNotNull();
        assertThat(foundBooking.getUser()).isEqualTo(user);
    }
}

