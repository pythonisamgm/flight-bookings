package com.flightbookings.flight_bookings.services;

import com.flightbookings.flight_bookings.models.Booking;
import com.flightbookings.flight_bookings.models.User;
import com.flightbookings.flight_bookings.repositories.IBookingRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BookingServiceImpl {

    private final IBookingRepository bookingRepository;


    public BookingServiceImpl(IBookingRepository bookingRepository) {
        this.bookingRepository = bookingRepository;
    }

    public Booking createBooking(Booking booking, User user) {
        booking.setUser(user);
        return bookingRepository.save(booking);
    }

    public Booking getBookingById(Long id) {
        Optional<Booking> booking = bookingRepository.findById(id);
        return booking.orElse(null);
    }

    public List<Booking> getAllBookings() {
        return bookingRepository.findAll();
    }

    public Booking updateBooking(Long id, Booking bookingDetails) {
        Optional<Booking> existingBooking = bookingRepository.findById(id);
        if (existingBooking.isPresent()) {
            Booking bookingToUpdate = existingBooking.get();
            bookingToUpdate.setFlight(bookingDetails.getFlight());
            bookingToUpdate.setPassenger(bookingDetails.getPassenger());
            bookingToUpdate.setDateOfBooking(bookingDetails.getDateOfBooking());
            return bookingRepository.save(bookingToUpdate);
        }
        return null;
    }

    public boolean deleteBooking(Long id) {
        Optional<Booking> booking = bookingRepository.findById(id);
        if (booking.isPresent()) {
            bookingRepository.deleteById(id);
            return true;
        }
        return false;
    }

    public List<Booking> getBookingsByUser(User user) {
        return bookingRepository.findByPassenger_User(user);
    }
}
