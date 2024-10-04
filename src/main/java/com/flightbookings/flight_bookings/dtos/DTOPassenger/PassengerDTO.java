package com.flightbookings.flight_bookings.dtos.DTOPassenger;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

@Schema(description = "Data Transfer Object for Passenger.")
public class PassengerDTO {

    @Schema(description = "The database generated passenger ID")
    private Long passengerId;

    @NotBlank(message = "Passenger name is mandatory")
    @Schema(description = "The name of the passenger")
    private String passengerName;

    @NotBlank(message = "Identity document number is mandatory")
    @Schema(description = "The identity document number of the passenger")
    private String identityDoc;

    @NotNull(message = "Telephone number is mandatory")
    @Schema(description = "The telephone number of the passenger")
    private Long telephone;

    @Schema(description = "The nationality of the passenger")
    private String nationality;

    @Schema(description = "The booking ID associated with this passenger")
    private Long bookingId;

    public PassengerDTO() {}

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
