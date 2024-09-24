package com.flightbookings.flight_bookings.models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;

@Entity
@Table(name = "Passenger")
public class Passenger {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long passengerId;

    @Column
    private String passengerName;

    @Column
    private String identityDoc;

    @Column
    private int telephone;

    @Column
    private String nationality;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @OneToOne
    @JsonBackReference("booking-passenger")
    private Booking booking;

    public Passenger() {
    }

    public Passenger(Long passengerId, String passengerName, String identityDoc, int telephone, String nationality, User user, Booking booking) {
        this.passengerId = passengerId;
        this.passengerName = passengerName;
        this.identityDoc = identityDoc;
        this.telephone = telephone;
        this.nationality = nationality;
        this.user = user;
        this.booking = booking;
    }

    public Long getPassengerId() {
        return passengerId;
    }

    public void setPassengerId(Long passengerId) {
        this.passengerId = passengerId;
    }

    public String getPassengerName() {
        return passengerName;
    }

    public void setPassengerName(String passengerName) {
        this.passengerName = passengerName;
    }

    public String getIdentityDoc() {
        return identityDoc;
    }

    public void setIdentityDoc(String identityDoc) {
        this.identityDoc = identityDoc;
    }

    public int getTelephone() {
        return telephone;
    }

    public void setTelephone(int telephone) {
        this.telephone = telephone;
    }

    public String getNationality() {
        return nationality;
    }

    public void setNationality(String nationality) {
        this.nationality = nationality;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Booking getBooking() {
        return booking;
    }

    public void setBooking(Booking booking) {
        this.booking = booking;
    }
}
