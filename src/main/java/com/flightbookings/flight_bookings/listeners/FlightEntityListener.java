/*package com.flightbookings.flight_bookings.listeners;


import com.flightbookings.flight_bookings.models.Flight;
import com.flightbookings.flight_bookings.services.SeatServiceImpl;
import com.flightbookings.flight_bookings.services.interfaces.SeatService;
import jakarta.annotation.PostConstruct;
import jakarta.persistence.PostPersist;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

@Component
public class FlightEntityListener {

    private static SeatService seatService;

    @Autowired
    private ApplicationContext applicationContext;

    @PostConstruct
    public void init() {
        seatService = applicationContext.getBean(SeatService.class);
    }

    @PostPersist
    public void initializeSeats(Flight flight) {
        seatService.initializeSeats(flight, flight.getNumRows());
    }
}
*/
