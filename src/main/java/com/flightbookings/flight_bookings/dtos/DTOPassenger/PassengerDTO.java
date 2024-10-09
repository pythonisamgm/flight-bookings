package com.flightbookings.flight_bookings.dtos.DTOPassenger;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

/**
 * Data Transfer Object for Passenger.
 * This class represents the data that will be transferred between the client and server
 * related to the Passenger entity.
 */
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

    /**
     * Default constructor for PassengerDTO.
     */
    public PassengerDTO() {}

    /**
     * Gets the passenger ID.
     *
     * @return the passenger ID
     */
    public Long getPassengerId() {
        return passengerId;
    }

    /**
     * Sets the passenger ID.
     *
     * @param passengerId the passenger ID to set
     */
    public void setPassengerId(Long passengerId) {
        this.passengerId = passengerId;
    }

    /**
     * Gets the name of the passenger.
     *
     * @return the passenger name
     */
    public String getPassengerName() {
        return passengerName;
    }

    /**
     * Sets the name of the passenger.
     *
     * @param passengerName the passenger name to set
     */
    public void setPassengerName(String passengerName) {
        this.passengerName = passengerName;
    }

    /**
     * Gets the identity document number of the passenger.
     *
     * @return the identity document number
     */
    public String getIdentityDoc() {
        return identityDoc;
    }

    /**
     * Sets the identity document number of the passenger.
     *
     * @param identityDoc the identity document number to set
     */
    public void setIdentityDoc(String identityDoc) {
        this.identityDoc = identityDoc;
    }

    /**
     * Gets the telephone number of the passenger.
     *
     * @return the telephone number
     */
    public Long getTelephone() {
        return telephone;
    }

    /**
     * Sets the telephone number of the passenger.
     *
     * @param telephone the telephone number to set
     */
    public void setTelephone(Long telephone) {
        this.telephone = telephone;
    }

    /**
     * Gets the nationality of the passenger.
     *
     * @return the nationality
     */
    public String getNationality() {
        return nationality;
    }

    /**
     * Sets the nationality of the passenger.
     *
     * @param nationality the nationality to set
     */
    public void setNationality(String nationality) {
        this.nationality = nationality;
    }

    /**
     * Gets the booking ID associated with this passenger.
     *
     * @return the booking ID
     */
    public Long getBookingId() {
        return bookingId;
    }

    /**
     * Sets the booking ID associated with this passenger.
     *
     * @param bookingId the booking ID to set
     */
    public void setBookingId(Long bookingId) {
        this.bookingId = bookingId;
    }
}
