package com.flightbookings.flight_bookings.dtos.DTOPassenger;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Data Transfer Object for Passenger.")
public class PassengerDTO {

    @Schema(description = "The database generated passenger ID")
    private Long passengerId;

    @Schema(description = "The name of the passenger")
    private String passengerName;

    @Schema(description = "The identity document number of the passenger")
    private String identityDoc;

    @Schema(description = "The telephone number of the passenger")
    private Long telephone;

    @Schema(description = "The nationality of the passenger")
    private String nationality;

    @Schema(description = "The booking ID associated with this passenger")
    private Long bookingId;

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

    public Long getBookingId() {
        return bookingId;
    }

    public void setBookingId(Long bookingId) {
        this.bookingId = bookingId;
    }
}
