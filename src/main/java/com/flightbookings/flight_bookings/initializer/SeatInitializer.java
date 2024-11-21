package com.flightbookings.flight_bookings.initializer;

import com.flightbookings.flight_bookings.services.interfaces.SeatService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

/**
 * Initializes seats for all flights when the application starts.
 */
@Component
public class SeatInitializer implements CommandLineRunner {

    private final SeatService seatService;

    /**
     * Constructs a SeatInitializer with the required SeatService.
     *
     * @param seatService the service to manage seat initialization.
     */
    public SeatInitializer(SeatService seatService) {
        this.seatService = seatService;
    }

    /**
     * Runs the seat initialization for all flights.
     *
     * @param args command-line arguments (not used).
     * @throws Exception in case of any error during initialization.
     */
    @Override
    public void run(String... args) throws Exception {
        seatService.initializeSeatsForAllFlights();
    }
}
