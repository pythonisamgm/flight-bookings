package com.flightbookings.flight_bookings.models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;


@Entity
@Table(name = "passenger")
@Schema(description = "All details about the Passenger entity.")
public class Passenger {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = "The database generated passenger ID")
    private Long passengerId;

    @Column
    @Schema(description = "The name of the passenger")
    private String passengerName;

    @Column
    @Schema(description = "The identity document number of the passenger")
    private String identityDoc;

    @Column
    @Schema(description = "The telephone number of the passenger")
    private Long telephone;

    @Column
    @Schema(description = "The nationality of the passenger")
    private String nationality;

    @OneToOne
    @JsonBackReference(value = "booking-passenger")
    @Schema(description = "The booking associated with this passenger")
    private Booking booking;

//    @Column
//    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy")
//    private LocalDate DOB;


    public Passenger() {
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

    public Long getTelephone() {
        return telephone;
    }

    public void setTelephone(Long telephone) {
        this.telephone = telephone;
    }

    public String getNationality() {
        return nationality;
    }

    public void setNationality(String nationality) {
        this.nationality = nationality;
    }

    public Booking getBooking() {
        return booking;
    }

    public void setBooking(Booking booking) {
        this.booking = booking;
    }

    public Passenger(Long passengerId, String passengerName, String identityDoc, Long telephone, String nationality, Booking booking) {
        this.passengerId = passengerId;
        this.passengerName = passengerName;
        this.identityDoc = identityDoc;
        this.telephone = telephone;
        this.nationality = nationality;
        this.booking = booking;

    }
}


